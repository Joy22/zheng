package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsLogMapper;
import com.zheng.upms.dao.model.UpmsLog;
import com.zheng.upms.dao.model.UpmsLogExample;

/**
* 降级实现UpmsLogService接口
* @author Joy
* @date 2018/4/9
*/
public class UpmsLogServiceMock extends BaseServiceMock<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

}
