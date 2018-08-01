package com.tingyun.start.listener;

import com.tingyun.lesson9.PersonService;
import com.tingyun.service.impl.PersonServiceImpl;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        TNonblockingServerSocket socket = null;
        try {
            socket = new TNonblockingServerSocket(8999);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        THsHaServer.Args arg = new THsHaServer.Args(socket).maxWorkerThreads(4).minWorkerThreads(2);

        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(arg);

        System.out.println("server begin started...");

        server.serve();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
