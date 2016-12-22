package com.infullstack.mdfs.communication.server.impl;

import com.infullstack.mdfs.communication.common.DefaultCommunicationInitializer;
import com.infullstack.mdfs.communication.common.ProtobufCommunicationInitializer;
import com.infullstack.mdfs.communication.server.CommunicationServer;
import io.netty.channel.ChannelHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import static com.infullstack.mdfs.common.utils.Constants.SSL;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class TesServer {


    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));
    static SslContext sslCtx;

//    @Before
    public void before() throws Exception {

        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
    }

//    @Test
    public void testDefaultServer() {
        ChannelHandler handler = new DefaultCommunicationServerHandler();
        ChannelHandler initializer = new DefaultCommunicationInitializer(sslCtx, handler);
        CommunicationServer server = new DefaultCommunicationServerImpl(PORT, initializer);
        server.start();
    }
    public void testProtobufServer() {
        ChannelHandler handler = new ProtobufCommunicationServerHandler();
        ChannelHandler initializer = new ProtobufCommunicationInitializer(sslCtx, handler);
        CommunicationServer server = new DefaultCommunicationServerImpl(PORT, initializer);
        server.start();
    }

    public static void main(String[] args){
        new TesServer().testProtobufServer();
    }
}
