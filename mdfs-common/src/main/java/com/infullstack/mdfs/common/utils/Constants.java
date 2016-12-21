package com.infullstack.mdfs.common.utils;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class Constants {

    public static String MDFS_DATA_DIR;
    public static String MDFS_REPLICATION;

    public static String CONFIG_MDFS_DATA_DIR = "mdfs.datanode.dir";
    public static String CONFIG_MDFS_REPLICATION = "mdfs.replication";

    public static final boolean SSL = System.getProperty("ssl") != null;



}
