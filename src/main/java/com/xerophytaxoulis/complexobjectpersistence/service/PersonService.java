package com.xerophytaxoulis.complexobjectpersistence.service;

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
        return personRepository.findByNameAndAddress(person.getName(), person.getAddress().getAddress())
            .singleOrEmpty()
            .switchIfEmpty(this.save(person));
    }

    public Flux<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Mono<Void> deleteAll() {
        return this.personRepository.deleteAll();
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
