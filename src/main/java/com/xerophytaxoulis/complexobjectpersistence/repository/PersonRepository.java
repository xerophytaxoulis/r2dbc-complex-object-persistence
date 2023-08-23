package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

    @Query("select p.*, a.address from person p join address a on p.address_id = a.id")
    Flux<Person> findAll();

    @Query("p.*, a.address from person p join address a on p.address_id = a.id where email = :email")
    Mono<Person> findByEmail(String email);
}
