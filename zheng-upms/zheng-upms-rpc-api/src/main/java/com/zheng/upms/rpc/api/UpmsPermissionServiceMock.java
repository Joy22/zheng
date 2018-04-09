package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsPermissionMapper;
import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsPermissionExample;

/**
* 降级实现UpmsPermissionService接口
* @author Joy
* @date 2018/4/9
*/
public class UpmsPermissionServiceMock extends BaseServiceMock<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements UpmsPermissionService {

}
