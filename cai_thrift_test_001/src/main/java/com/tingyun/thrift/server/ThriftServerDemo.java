package com.tingyun.thrift.server;

import com.thrift.TestQry;
import com.tingyun.thrift.impl.QueryImp;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServerDemo {

    private final static int DEFAULT_PORT = 30001;

    // TServer 一般服务器的通用接口。
    private static TServer server = null;

    public static void main(String[] args) {
        try {
            // TNonblockingServerSocket 包装ServerSocketChannel
            TNonblockingServerSocket socket = new TNonblockingServerSocket(DEFAULT_PORT);

            //获得 .thrift 文件中定义的 TestQry 服务的 processor 。
            TestQry.Processor processor = new TestQry.Processor(new QueryImp());

            TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
            arg.protocolFactory(new TBinaryProtocol.Factory()); // TBinaryProtocol 用于节约的二进制协议实现。
            arg.transportFactory(new TFramedTransport.Factory());   // TFramedTransport 是一种缓冲的TTransport，它可以在每次消息之前以一个4字节的帧大小来确保一个完整的读取消息。
            arg.processorFactory(new TProcessorFactory(processor)); // TProcessor 处理器是一个通用对象，它在输入流上操作，并写入一些输出流。

            // TNonblockingServer 非阻塞TServer实现。这允许在所有连接的客户端之间进行调用。这个服务器本质上是单线程的。如果您想要一个有限的线程池，再加上调用-公平性，请参见THsHaServer。要使用这个服务器，您必须在最外层的传输中使用一个TFramedTransport，否则这个服务器将无法确定整个方法调用何时被读取。客户端还必须使用TFramedTransport。
            server = new TNonblockingServer(arg);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
