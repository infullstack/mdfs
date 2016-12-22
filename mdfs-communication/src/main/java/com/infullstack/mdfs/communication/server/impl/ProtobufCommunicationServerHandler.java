package com.infullstack.mdfs.communication.server.impl;

import com.infullstack.mdfs.communication.pojo.SubscribeReqProto;
import com.infullstack.mdfs.communication.pojo.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class ProtobufCommunicationServerHandler  extends SimpleChannelInboundHandler {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        //System.out.println("SubReqServerHandler channelRead:"+ req.getUserName());
        if("leeka".equalsIgnoreCase(req.getUserName())){
            System.out.println("service accept client subscribe req:["+ req +"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode("0");
        builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}