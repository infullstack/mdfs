package com.infullstack.mdfs.namenode.controler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class NameNodeControler {

    public static void main(String args[]) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // Server服务启动器
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置一个处理客户端消息和各种消息事件的类(Handler)
        bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer(){
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new NameNodeControlerHandler());
            }
        });
        // 开放8000端口供客户端访问。
        bootstrap.bind(new InetSocketAddress("127.0.0.1",8009));
    }

}
