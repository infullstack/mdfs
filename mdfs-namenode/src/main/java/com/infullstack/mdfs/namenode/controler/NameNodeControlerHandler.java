package com.infullstack.mdfs.namenode.controler;

import com.infullstack.mdfs.communication.server.impl.DefaultCommunicationServerHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class NameNodeControlerHandler extends DefaultCommunicationServerHandler {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("server recive message : " + msg);

        // Generate and write a response.
        String response;
        boolean close = false;
        response = "client say '" + msg + "'?\r\n";





        ChannelFuture future = ctx.write(response);

        future.addListener(ChannelFutureListener.CLOSE);

        ReferenceCountUtil.release(msg); // (2)
    }

}