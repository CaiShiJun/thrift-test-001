package com.tingyun.thrift.client;

import com.thrift.QryResult;
import com.thrift.TestQry;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClientDemo {
    private final static int DEFAULT_QRY_CODE = 1;

    public static void main(String[] args) {
        try {
            TTransport tTransport = getTTransport();

            // TProtocol 协议接口定义。
            TProtocol protocol = new TBinaryProtocol(tTransport);   // TBinaryProtocol 用于节约的二进制协议实现。
            TestQry.Client client = new TestQry.Client(protocol);   // 由 .thrift 文件生成的 Client 类。
            QryResult result = client.qryTest(DEFAULT_QRY_CODE);    // 由 .thrift 文件定义的方法生成的方法。
            System.out.println("code=" + result.code + " msg=" + result.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TTransport getTTransport() throws Exception {
        try {
            // TTransport 封装I/O层的泛型类。这基本上是一个关于Java输入/输出流组合功能的薄包装。
            TTransport tTransport = getTTransport("127.0.0.1", 30001, 5000);

            if (!tTransport.isOpen()) {     //查询传输是否打开。
                tTransport.open();      //打开阅读/写作的工具。
            }
            return tTransport;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TTransport getTTransport(String host, int port, int timeout) {
        // TSocket TTransport接口的套接字实现。评论很快!
        final TSocket tSocket = new TSocket(host, port, timeout);   //创建一个新的未连接的套接字，该套接字将连接到给定端口上的给定主机。
        final TTransport transport = new TFramedTransport(tSocket); // TFramedTransport 是一种缓冲的TTransport，它可以在每次消息之前以一个4字节的帧大小来确保一个完整的读取消息。
        return transport;
    }
}
