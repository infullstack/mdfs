package com.infullstack.mdfs.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;


/**
 * Created by Ray on 2016/12/20 0020.
 */
public class FileSenderHander extends SimpleChannelInboundHandler<String> {

    Logger logger= Logger.getLogger(FileSenderHander.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
