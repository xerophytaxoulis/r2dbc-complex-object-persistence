package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.stereotype.Service;

import com.xerophytaxoulis.complexobjectpersistence.domain.Subscription;
import com.xerophytaxoulis.complexobjectpersistence.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Mono<Subscription> save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Flux<Subscription> findSubscriptionsBySubscriberId(Integer subscriberId) {
        return subscriptionRepository.findSubscriptionsBySubscriberId(subscriberId);
    }
    
}
