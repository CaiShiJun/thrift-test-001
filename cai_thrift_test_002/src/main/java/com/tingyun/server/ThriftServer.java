package com.tingyun.server;

import com.tingyun.lesson9.PersonService;
import com.tingyun.service.impl.PersonServiceImpl;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    public static void main(String[] args) throws TTransportException {

        TNonblockingServerSocket socket = new TNonblockingServerSocket(8999);

        THsHaServer.Args arg = new THsHaServer.Args(socket).maxWorkerThreads(4).minWorkerThreads(2);

        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(arg);

        System.out.println("server begin started...");

        server.serve();
    }
}
