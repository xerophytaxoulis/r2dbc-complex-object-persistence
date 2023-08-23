package com.xerophytaxoulis.complexobjectpersistence.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private Integer id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @Transient
    private Address address;

    @Transient
    private List<Subscription> subscriptions;

    @Transient
    private List<Person> friends;
}
