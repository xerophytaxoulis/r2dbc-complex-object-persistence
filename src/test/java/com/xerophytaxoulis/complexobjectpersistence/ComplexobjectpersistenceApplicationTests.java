package com.xerophytaxoulis.complexobjectpersistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;
import com.xerophytaxoulis.complexobjectpersistence.domain.Person;
import com.xerophytaxoulis.complexobjectpersistence.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class ComplexobjectpersistenceApplicationTests {

    @Autowired
    private PersonService personService;

    private final Flux<Address> addresses = Flux.just(new Address(null, "nice street, no. 1"), new Address(null, "Hellway 2, Tartarus"));

    private final Flux<Person> people = Flux.just(new Person(null, "Peter", addresses.take(1).blockFirst(), null),
        new Person(null, "Hans", addresses.skip(1).blockFirst(), null));

	@Test
	void savePerson() {
        var saved = people.flatMap(personService::save);
        StepVerifier.create(saved)
            .expectNextMatches(person -> person.getId() != null)
            .expectNextMatches(person -> person.getId() != null)
            .verifyComplete();
        StepVerifier.create(saved).expectNextCount(2).verifyComplete();

        var loaded = personService.findAll();
        loaded.subscribe(System.out::println);
        StepVerifier.create(loaded).expectNextCount(2).verifyComplete();
	}

}
