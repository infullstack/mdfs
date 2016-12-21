package com.infullstack.mdfs.common.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ray on 2016/12/20 0020.
 */
public class PropertiesUtils {

    private static Properties properties = new Properties();

    static Logger logger = Logger.getLogger(PropertiesUtils.class);

    public static void load(String fileName) {
        Properties pros = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtils.class.getClassLoader().getResource(fileName).openStream();
            pros.load(in);
            properties.putAll(pros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        PropertiesUtils.load("mdfs-site.properties");
        MDFSConstants.MDFS_DATA_DIR = PropertiesUtils.get(MDFSConstants.CONFIG_MDFS_DATA_DIR);
        logger.info(MDFSConstants.MDFS_DATA_DIR );
        MDFSConstants.MDFS_REPLICATION = PropertiesUtils.get(MDFSConstants.CONFIG_MDFS_REPLICATION);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}
