package com.tingyun.service.impl;

import com.tingyun.lesson9.DataException;
import com.tingyun.lesson9.Person;
import com.tingyun.lesson9.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByName(String name) throws DataException, TException {
        return new Person().setName("getPersonByName(String name)：success");
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println(person);
    }
}
