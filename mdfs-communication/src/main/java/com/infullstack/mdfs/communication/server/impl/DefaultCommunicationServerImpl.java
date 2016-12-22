package com.infullstack.mdfs.communication.server.impl;

import com.infullstack.mdfs.common.utils.Constants;
import com.infullstack.mdfs.communication.server.CommunicationServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class DefaultCommunicationServerImpl implements CommunicationServer {

    private int port;
    private ChannelHandler handler;

    public DefaultCommunicationServerImpl(int port, ChannelHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    public boolean start() {
        // Configure SSL.
        final SslContext sslCtx;
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            if (Constants.SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
            } else {
                sslCtx = null;
            }
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(handler);
            b.bind(port).sync().channel().closeFuture().sync();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
        return true;
    }
}
