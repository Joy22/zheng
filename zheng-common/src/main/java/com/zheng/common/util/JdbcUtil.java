package com.zheng.common.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具类
 * @author Joy
 * @date 2018/2/28
 */
public class JdbcUtil {

    /**
     * 定义数据库的链接
     */
    private Connection conn;

    /**
     * 定义sql语句的执行对象
     */
    private PreparedStatement pstmt;

    /**
     * 定义查询返回的结果集合
     */
    private ResultSet rs;

    /**
     * 初始化
     * @param driver
     * @param url
     * @param username
     * @param password
     */
    public JdbcUtil(String driver, String url, String username, String password){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean updateByParams(String sql, List params) throws SQLException{
        pstmt = conn.prepareStatement(sql);
        int index = 1;
        if(null != params && !params.isEmpty()){
            for(int i = 0; i< params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        int result = pstmt.executeUpdate();
        return result > 0;
    }

    /**
     * 查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map> selectByParams(String sql, List params) throws SQLException{
        List<Map> list = new ArrayList<Map>();
        int index = 1;
        pstmt = conn.prepareStatement(sql);
        if(null != params && !params.isEmpty()){
            for(int i = 0; i < params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int colsLen = metaData.getColumnCount();
        while (rs.next()){
            Map map = new HashMap(colsLen);
            for(int i = 1; i <= colsLen; i++){
                String columnName = metaData.getColumnName(i);
                Object columnValue = rs.getObject(columnName);
                if(null == columnValue){
                    columnValue = "";
                }
                map.put(columnName, columnValue);
            }
            list.add(map);
        }
        return list;
    }

    public void release(){
        try {
            if (null != rs){
                rs.close();
            }
            if (null != pstmt){
                pstmt.close();
            }
            if(null != conn){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("释放数据库连接");
    }
}
