package com.zheng.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（数据源切换）
 * @author Joy
 * @date 2018/3/2
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final static Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = getDateSource();
        LOGGER.info("当前操作使用的数据源：{}", dataSource);
        return dataSource;
    }

    /**
     * 设置数据源
     * @param datasource
     */
    public static void setDatasource(String datasource){
        CONTEXT_HOLDER.set(datasource);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDateSource(){
        String dataSource = CONTEXT_HOLDER.get();
        // 如果没有指定数据源，使用默认数据源
        if( null == dataSource){
            DynamicDataSource.setDatasource(DataSourceEnum.getDefault());
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }
}
