package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsLogMapper;
import com.zheng.upms.dao.model.UpmsLog;
import com.zheng.upms.dao.model.UpmsLogExample;
import com.zheng.upms.rpc.api.UpmsLogService;

/**
* 降级实现UpmsLogService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsLogServiceMock extends BaseServiceMock<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

}
