package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsSystemMapper;
import com.zheng.upms.dao.model.UpmsSystem;
import com.zheng.upms.dao.model.UpmsSystemExample;

/**
* 降级实现UpmsSystemService接口
* @author Joy
* @date 2018/4/9
*/
public class UpmsSystemServiceMock extends BaseServiceMock<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements UpmsSystemService {

    @Override
    public UpmsSystem selectUpmsSystemByName(String name) {
        return null;
    }
}
