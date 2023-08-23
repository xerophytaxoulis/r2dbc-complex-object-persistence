package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Subscription;

public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer> {
}
