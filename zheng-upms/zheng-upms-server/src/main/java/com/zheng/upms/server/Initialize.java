package com.zheng.upms.server;

import com.zheng.common.base.BaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统接口
 * @author Joy
 * @date 2018/3/2
 */
public class Initialize implements BaseInterface{

    private static final Logger LOGGER = LoggerFactory.getLogger(Initialize.class);
    @Override
    public void init() {
        LOGGER.info(">>>>> 系统初始化");
    }
}
