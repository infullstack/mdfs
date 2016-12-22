package com.infullstack.mdfs.communication.client.impl;


import com.infullstack.mdfs.communication.client.CommunicationClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class ProtobufCommunicationClientImpl implements CommunicationClient {

    private ChannelHandler initializer;

    public ProtobufCommunicationClientImpl(ChannelHandler initializer) {
        this.initializer = initializer;
    }

    @Override
    public String sendMessage(String ip, int port, String message) {
        // Configure SSL.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(initializer);
            // Start the connection attempt.
            ChannelFuture f = b.connect(ip, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return null;
    }
}
