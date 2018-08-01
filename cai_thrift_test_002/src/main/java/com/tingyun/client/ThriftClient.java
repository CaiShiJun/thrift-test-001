package com.tingyun.client;

import com.tingyun.lesson9.Person;
import com.tingyun.lesson9.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8999), 600);

        TProtocol protocol = new TCompactProtocol(transport);

        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();
            Person person = client.getPersonByName("王五");
            System.out.println(person.getAge() + ";;" + person.getName() + ";;" + person.isIsMarried());

            Person p = new Person();
            p.setAge(56);
            p.setName("田七");
            p.setIsMarried(false);

            client.savePerson(p);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            transport.close();
        }
    }
}
