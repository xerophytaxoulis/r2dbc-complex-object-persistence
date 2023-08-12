package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;

public interface AddressRepository extends ReactiveCrudRepository<Address, Long> { }
