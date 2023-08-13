package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Person;

import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

    @Query("select p.*, a.address from person p join address a on p.address_id = a.id")
    Flux<Person> findAll();

    @Query("select p.* from person p join address a on p.address_id = a.id where p.name = :name and a.address = :address")
    Flux<Person> findByNameAndAddress(String name, String address);
}
