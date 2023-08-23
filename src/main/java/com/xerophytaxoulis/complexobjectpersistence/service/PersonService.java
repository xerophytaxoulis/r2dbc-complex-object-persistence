package com.xerophytaxoulis.complexobjectpersistence.service;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xerophytaxoulis.complexobjectpersistence.domain.Person;
import com.xerophytaxoulis.complexobjectpersistence.domain.PersonSubscription;
import com.xerophytaxoulis.complexobjectpersistence.domain.Subscription;
import com.xerophytaxoulis.complexobjectpersistence.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressService addressService;    
    private final SubscriptionService subscriptionService;
    private final PersonSubscriptionService personSubscriptionService;

    @Transactional
    public Mono<Person> save(Person person) {
        return Flux.fromIterable(person.getSubscriptions())
            .flatMap(subscriptionService::save).collectList()
            .doOnNext(person::setSubscriptions)
            .then(addressService.upsert(person.getAddress()))
            .doOnNext(person::setAddress)
            .then(personRepository.save(person))
            .flatMap(this::capturePersonSubscriptionRelation);
    }

    @Transactional
    public Mono<Person> upsert(Person person) {
        return personRepository.findByEmail(person.getEmail())
            .flatMap(this::getSubscriptions)
            // email is unique identifier: check for updates of name, address and subscriptions here
            .filter(Predicate.not(person::equals))
            // better do delete old entry and just save new new one or to update fields
            // .flatMap(personRepository::updatePerson)
            .switchIfEmpty(this.save(person));
    }

    public Flux<Person> findAll() {
        return personRepository.findAll()
            .flatMap(this::getSubscriptions);
    }

    public Mono<Void> deleteAll() {
        return this.personRepository.deleteAll();
    }

    private Mono<Person> getSubscriptions(Person person) {
        return subscriptionService.findSubscriptionsBySubscriberId(person.getId())
            .collectList()
            .doOnNext(person::setSubscriptions)
            .then(Mono.just(person));
    }
    
    private Mono<Person> capturePersonSubscriptionRelation(Person person) {
        return Flux.<Integer>generate(sink -> sink.next(person.getId()))
                .zipWith(Flux.fromStream(person.getSubscriptions().stream().map(Subscription::getId)))
                // Flux<Tuple2<personId, subscriptionId>>
                .map(tuple -> new PersonSubscription(null, tuple.getT1(), tuple.getT2()))
                .flatMap(personSubscriptionService::save)
                .then(Mono.just(person));
    }
}
