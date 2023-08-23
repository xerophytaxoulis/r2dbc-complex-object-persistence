package com.xerophytaxoulis.complexobjectpersistence.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.xerophytaxoulis.complexobjectpersistence.domain.Subscription;

import reactor.core.publisher.Flux;

public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer> {

    @Query("select s.* from subscription s join person_subscription ps on s.id = ps.subscription_id where person_id = :subscriberId")
    Flux<Subscription> findSubscriptionsBySubscriberId(Integer subscriberId);
}
