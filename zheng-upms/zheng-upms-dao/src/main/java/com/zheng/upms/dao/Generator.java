package com.zheng.upms.dao;

import com.zheng.common.util.MybatisGeneratorUtil;
import com.zheng.common.util.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成类
 * @author Joy
 * @date 2018/2/27
 */
public class Generator {

    // 根据命名规范修改此常量即可
    private static String MODULE = "zheng-upms";
    private static String DATABASE = "zheng";
    private static String TABLE_PREFIX = "upms_";
    private static String PACKAGE_NAME = "com.zheng.upms";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");

    /**
     * 需要insert后返回主键的表配置，key:表名，value:主键名
     */
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<String, String>();
    static {
        LAST_INSERT_ID_TABLES.put("upms_user", "user_id");
    }

    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}