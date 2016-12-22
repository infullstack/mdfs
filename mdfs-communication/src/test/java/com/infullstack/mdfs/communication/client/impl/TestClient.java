package com.infullstack.mdfs.communication.client.impl;

import com.infullstack.mdfs.communication.client.CommunicationClient;
import com.infullstack.mdfs.communication.common.DefaultCommunicationInitializer;
import com.infullstack.mdfs.communication.common.ProtobufCommunicationInitializer;
import io.netty.channel.ChannelHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.junit.Before;
import org.junit.Test;

import static com.infullstack.mdfs.common.utils.Constants.SSL;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class TestClient {

    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));
    SslContext sslCtx;

    @Before
    public void before() throws Exception {

        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
    }
    @Test
    public void testProtobufServer() {
        ChannelHandler handler = new ProtobufCommunicationClientHandler();
        String ip = "127.0.0.1";
        ChannelHandler initializer = new ProtobufCommunicationInitializer(sslCtx, handler);
        CommunicationClient client = new ProtobufCommunicationClientImpl(initializer);
        client.sendMessage(ip, PORT, "hello");
    }

//    @Test
    public void testDefuServer() {
        ChannelHandler handler = new DefaultCommunicationClientHandler();
        String ip = "127.0.0.1";
        ChannelHandler initializer = new DefaultCommunicationInitializer(sslCtx, handler);
        CommunicationClient client = new DefaultCommunicationClientImpl(initializer);
        client.sendMessage(ip, PORT, "hello");
    }
}
