package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsUserPermissionMapper;
import com.zheng.upms.dao.model.UpmsUserPermission;
import com.zheng.upms.dao.model.UpmsUserPermissionExample;
import com.zheng.upms.rpc.api.UpmsUserPermissionService;

/**
* 降级实现UpmsUserPermissionService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsUserPermissionServiceMock extends BaseServiceMock<UpmsUserPermissionMapper, UpmsUserPermission, UpmsUserPermissionExample> implements UpmsUserPermissionService {

}
