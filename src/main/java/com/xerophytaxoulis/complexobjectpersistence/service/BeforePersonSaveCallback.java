package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Component;

import com.xerophytaxoulis.complexobjectpersistence.domain.Person;

import reactor.core.publisher.Mono;

@Component
public class BeforePersonSaveCallback implements BeforeSaveCallback<Person> {
    @Override
    public Mono<Person> onBeforeSave(Person entity, OutboundRow row, SqlIdentifier table) {
        row.put("ADDRESS_ID", Parameter.from(entity.getAddress().getId()));
        return Mono.just(entity);
    }
}
