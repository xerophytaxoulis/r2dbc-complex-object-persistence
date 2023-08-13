package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.stereotype.Service;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;
import com.xerophytaxoulis.complexobjectpersistence.repository.AddressRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Mono<Address> save(Address address) {
        return this.addressRepository.save(address);
    }

    public Mono<Address> upsert(Address address) {
        return this.addressRepository.findByAddress(address.getAddress())
            .singleOrEmpty()
            .switchIfEmpty(this.save(address));
    }

    public Flux<Address> findAll() {
        return this.addressRepository.findAll();
    }
    
}
