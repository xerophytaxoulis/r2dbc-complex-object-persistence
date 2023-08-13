package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.stereotype.Service;

import com.xerophytaxoulis.complexobjectpersistence.domain.Person;
import com.xerophytaxoulis.complexobjectpersistence.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressService addressService;    

    public Mono<Person> save(Person person) {
        return addressService.upsert(person.getAddress()).flatMap(address -> {
            person.setAddress(address);
            return personRepository.save(person);}
        );
    }

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
    
}
