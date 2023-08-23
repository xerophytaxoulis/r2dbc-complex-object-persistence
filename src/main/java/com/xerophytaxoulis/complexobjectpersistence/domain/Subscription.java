package com.xerophytaxoulis.complexobjectpersistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    private Integer id;

    private String subscriptionProviderName;
}
