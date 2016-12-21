package com.infullstack.mdfs.communication.client.impl;


import com.infullstack.mdfs.communication.client.CommunicationClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class DefaultCommunicationClientImpl implements CommunicationClient {

    private ChannelHandler initializer;

    public DefaultCommunicationClientImpl(ChannelHandler initializer) {
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
            Channel ch = b.connect(ip, port).sync().channel();
            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            // Sends the received line to the server.
            lastWriteFuture = ch.writeAndFlush(message + "\r\n");
            // If user typed the 'bye' command, wait until the server closes
            // the connection.
            ch.closeFuture().sync();
            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return null;
    }
}
