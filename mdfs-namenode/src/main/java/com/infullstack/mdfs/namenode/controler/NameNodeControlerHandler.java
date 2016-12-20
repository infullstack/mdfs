package com.infullstack.mdfs.namenode.controler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class NameNodeControlerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("server recive message : " + msg);
        ReferenceCountUtil.release(msg); // (2)
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Hello world, I'm server.");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}