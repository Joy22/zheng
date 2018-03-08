package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsRolePermissionMapper;
import com.zheng.upms.dao.model.UpmsRolePermission;
import com.zheng.upms.dao.model.UpmsRolePermissionExample;
import com.zheng.upms.rpc.api.UpmsRolePermissionService;

/**
* 降级实现UpmsRolePermissionService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsRolePermissionServiceMock extends BaseServiceMock<UpmsRolePermissionMapper, UpmsRolePermission, UpmsRolePermissionExample> implements UpmsRolePermissionService {

}
