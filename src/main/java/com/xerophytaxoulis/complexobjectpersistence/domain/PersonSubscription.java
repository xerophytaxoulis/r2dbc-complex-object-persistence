package com.xerophytaxoulis.complexobjectpersistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table
@Data
public class PersonSubscription {

    @Id
    private Integer id;

    private Integer personId;

    private Integer subscriptionId;
}
