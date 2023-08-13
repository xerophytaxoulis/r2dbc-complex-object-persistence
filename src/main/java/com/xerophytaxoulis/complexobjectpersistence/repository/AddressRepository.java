package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;

import reactor.core.publisher.Flux;

public interface AddressRepository extends ReactiveCrudRepository<Address, Integer> {

    @Query("select * from address a where a.address = :address")
    Flux<Address> findByAddress(String address);
}
