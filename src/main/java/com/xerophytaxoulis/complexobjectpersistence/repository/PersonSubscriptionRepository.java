package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.PersonSubscription;

public interface PersonSubscriptionRepository extends ReactiveCrudRepository<PersonSubscription, Integer> {
}
