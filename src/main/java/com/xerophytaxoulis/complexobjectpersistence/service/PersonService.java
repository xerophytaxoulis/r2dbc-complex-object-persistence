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

    public Mono<Person> save(Person p) {
        return addressService.upsert(p.getAddress()).flatMap(a -> {
            p.setAddress(a);
            // Flux.fromIterable(p.getFriends()).flatMap(this::save);
            return personRepository.save(p);}
        );
    }

    public Flux<Person> findAll() {
        return this.personRepository.findAll();
    }
    
}
