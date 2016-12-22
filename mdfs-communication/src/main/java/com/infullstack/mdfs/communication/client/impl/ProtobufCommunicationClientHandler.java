package com.infullstack.mdfs.communication.client.impl;

import com.infullstack.mdfs.communication.pojo.SubscribeReqProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class ProtobufCommunicationClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = Logger.getLogger(ProtobufCommunicationClientHandler.class.getName());

    public ProtobufCommunicationClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(req(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq req(int i) {
        SubscribeReqProto.SubscribeReq.Builder r = SubscribeReqProto.SubscribeReq.newBuilder();
        r.setSubReqID(i);
        r.setProductName("Netty Book" + i);
        r.setUserName("leeka");

        List<String> address = new ArrayList<String>();
        address.add("Nanjing");
        address.add("Beijing");
        r.addAllAddress(address);
        return r.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("receive server response:[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warn("unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }

}
