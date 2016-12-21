package com.infullstack.mdfs.communication.client;

/**
 * Created by Ray on 2016/12/21 0021.
 */
public interface CommunicationClient {

    public String sendMessage(String ip, int port, String message);

}
