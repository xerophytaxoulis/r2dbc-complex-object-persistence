package com.xerophytaxoulis.complexobjectpersistence;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;
import com.xerophytaxoulis.complexobjectpersistence.domain.Person;
import com.xerophytaxoulis.complexobjectpersistence.service.AddressService;
import com.xerophytaxoulis.complexobjectpersistence.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ComplexobjectpersistenceApplicationTests {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    private final List<Address> addresses = List.of(new Address(null, "nice street, no. 1"), new Address(null, "Hellway 2, Tartarus"));

    private final List<Person> people = List.of(new Person(null, "Peter", addresses.get(0), null, null),
        new Person(null, "Hans", addresses.get(1), null, null));

    @BeforeEach
    void resetDatabase() {
        var deleteAllPeople = personService.deleteAll();
        var deleteAllAddresses = addressService.deleteAll();
        StepVerifier.create(deleteAllPeople).verifyComplete();
        StepVerifier.create(personService.findAll()).expectNextCount(0).verifyComplete();
        
        StepVerifier.create(deleteAllAddresses).verifyComplete();
        StepVerifier.create(addressService.findAll()).expectNextCount(0).verifyComplete();
    }

    @Test
    void upsertAddress() {
        var address = Mono.just(new Address(null, "test address, house no. 10"));
        var address2 = Mono.just(new Address(null, "test address, house no. 11"));

        var saved = address.flatMap(addressService::upsert);
        var saved2 = address2.flatMap(addressService::upsert);
        StepVerifier.create(saved).expectNextCount(1).verifyComplete();
        StepVerifier.create(addressService.findAll()).expectNextCount(1).verifyComplete();

        StepVerifier.create(saved2).expectNextCount(1).verifyComplete();

        StepVerifier.create(addressService.findAll()).expectNextCount(2).verifyComplete();

        StepVerifier.create(saved).expectNextCount(1).verifyComplete();

        StepVerifier.create(addressService.findAll()).expectNextCount(2).verifyComplete();
    }

    @Test
    void upsertPerson() {
        var saved = Flux.fromIterable(people).flatMap(personService::upsert);
        StepVerifier.create(saved).expectNextCount(2).verifyComplete();

        StepVerifier.create(Mono.just(people.get(0)).flatMap(personService::upsert)).expectNextCount(1).verifyComplete();
        StepVerifier.create(personService.findAll()).expectNextCount(2).verifyComplete();
    }

	@Test
	void savePerson() {
        var saved = Flux.fromIterable(people).flatMap(personService::save);
        StepVerifier.create(saved)
            .expectNextMatches(person -> person.getId() != null)
            .expectNextMatches(person -> person.getId() != null)
            .verifyComplete();

        var loaded = personService.findAll();
        loaded.subscribe(System.out::println);
        StepVerifier.create(loaded).expectNextCount(2).verifyComplete();
	}

}
