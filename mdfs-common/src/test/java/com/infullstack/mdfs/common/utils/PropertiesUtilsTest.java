package com.infullstack.mdfs.common.utils;

import static org.junit.Assert.assertNotNull;


/**
 * Created by Ray on 2016/12/20 0020.
 */
class PropertiesUtilsTest {

    @org.junit.Test
    public void testSimple() {
        PropertiesUtils.load("test.properties");
        assertNotNull(PropertiesUtils.get("name"));
        assertNotNull(PropertiesUtils.get("description"));
    }

}
