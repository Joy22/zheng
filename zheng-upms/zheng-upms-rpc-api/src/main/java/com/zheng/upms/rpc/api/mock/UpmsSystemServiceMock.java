package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsSystemMapper;
import com.zheng.upms.dao.model.UpmsSystem;
import com.zheng.upms.dao.model.UpmsSystemExample;
import com.zheng.upms.rpc.api.UpmsSystemService;

/**
* 降级实现UpmsSystemService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsSystemServiceMock extends BaseServiceMock<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements UpmsSystemService {

    @Override
    public UpmsSystem selectUpmsSystemByName(String name) {
        return null;
    }
}
