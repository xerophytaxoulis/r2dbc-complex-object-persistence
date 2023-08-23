package com.xerophytaxoulis.complexobjectpersistence.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.xerophytaxoulis.complexobjectpersistence.domain.Address;
import com.xerophytaxoulis.complexobjectpersistence.domain.Person;

import io.r2dbc.spi.Row;

@ReadingConverter
public class PersonReadConverter implements Converter<Row, Person> {

    @Override
    public Person convert(Row row) {
        var address = new Address();
        address.setId(row.get("ADDRESS_ID", Integer.class));
        address.setAddress(row.get("ADDRESS", String.class));

        var person = new Person();
        person.setId(row.get("ID", Integer.class));
        person.setEmail(row.get("EMAIL", String.class));
        person.setName(row.get("NAME", String.class));
        person.setAddress(address);

        return person;
    }
    
}
