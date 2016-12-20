package com.infullstack.mdfs.namenode.controler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class NameNodeControllerClientHandler implements ChannelHandler {
    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush("hello,i'm test");
        logger.info("Hello world, I'm client.");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
