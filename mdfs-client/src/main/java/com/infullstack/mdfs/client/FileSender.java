package com.infullstack.mdfs.client;

import com.alibaba.fastjson.JSONObject;
import com.infullstack.mdfs.common.utils.TransferFile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class FileSender {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8992" : "8023"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new FileSenderInitializer(sslCtx));

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();
            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            // Sends the received line to the server.
            ByteBuf a;
            TransferFile file = new TransferFile();
            file.setFileName("1.txt");
            file.setPath("D:\\infullstack\\netty\\testdata\\");
            file.setTargetFileName("test.txt");
            file.setTargetPath("\\test\\");
            lastWriteFuture = ch.writeAndFlush(JSONObject.toJSONString(file)+ "\r\n");
            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            group.shutdownGracefully();
        }
    }


}
