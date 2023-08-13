package com.xerophytaxoulis.complexobjectpersistence.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class PersonFriend {

    @Id
    private Integer id;

    private Integer personId;

    private Integer friendId;
    
}
