package com.infullstack.mdfs.communication.common;

import com.infullstack.mdfs.communication.pojo.SubscribeReqProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslContext;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public class ProtobufCommunicationInitializer extends ChannelInitializer<SocketChannel> {

    private final ChannelHandler handler;

    private final SslContext sslCtx;

    public ProtobufCommunicationInitializer(SslContext sslCtx, ChannelHandler handler) {
        this.sslCtx = sslCtx;
        this.handler = handler;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeline
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(
                        SubscribeReqProto.SubscribeReq.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder());
        // and then business logic.
        pipeline.addLast(handler);
    }

}