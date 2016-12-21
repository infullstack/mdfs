package com.infullstack.mdfs.datanode.file;

import com.alibaba.fastjson.JSONObject;
import com.infullstack.mdfs.common.utils.MDFSConstants;
import com.infullstack.mdfs.common.utils.TransferFile;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class FileReceiverHandler extends SimpleChannelInboundHandler<String> {

    Logger logger= Logger.getLogger(FileReceiverHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("HELO: Type the path of the file to retrieve.\n");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        TransferFile tFile= JSONObject.parseObject(msg,TransferFile.class);

        logger.info("receive mesage");
        RandomAccessFile raf = null;
        long length = -1;
        String fileName=tFile.getFileName();
        try {
            raf = new RandomAccessFile(tFile.getPath()+tFile.getFileName(), "r");
            length = raf.length();
        } catch (Exception e) {
            ctx.writeAndFlush("ERR: " + e.getClass().getSimpleName() + ": " + e.getMessage() + '\n');
            return;
        } finally {
            if (length < 0 && raf != null) {
                raf.close();
            }
        }
        logger.info(MDFSConstants.MDFS_DATA_DIR );
        File a=new File(MDFSConstants.MDFS_DATA_DIR+tFile.getTargetPath());
        a.mkdir();
        logger.info(MDFSConstants.MDFS_DATA_DIR+tFile.getTargetPath()+tFile.getTargetFileName());
        a=new File(MDFSConstants.MDFS_DATA_DIR+tFile.getTargetPath()+tFile.getTargetFileName());
        a.createNewFile();
        FileChannel toChannel = new FileOutputStream(MDFSConstants.MDFS_DATA_DIR+tFile.getTargetPath()+tFile.getTargetFileName()).getChannel();
        if (ctx.pipeline().get(SslHandler.class) == null) {
            // SSL not enabled - can use zero-copy file transfer.
//            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
            toChannel.transferFrom(raf.getChannel(),0,raf.length());
        } else {
            // SSL enabled - cannot use zero-copy file transfer.TODO
            ctx.write(new ChunkedFile(raf));
        }
        ctx.write("OK: " + raf.length() + '\n');
        ctx.writeAndFlush("\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            ctx.writeAndFlush("ERR: " +
                    cause.getClass().getSimpleName() + ": " +
                    cause.getMessage() + '\n').addListener(ChannelFutureListener.CLOSE);
        }
    }
}
