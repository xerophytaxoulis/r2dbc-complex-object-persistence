package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.stereotype.Service;

import com.xerophytaxoulis.complexobjectpersistence.domain.PersonSubscription;
import com.xerophytaxoulis.complexobjectpersistence.repository.PersonSubscriptionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonSubscriptionService {

    private final PersonSubscriptionRepository personSubscriptionRepository;

    public Mono<PersonSubscription> save(PersonSubscription personSubscription) {
        return personSubscriptionRepository.save(personSubscription);
    }
    
}
