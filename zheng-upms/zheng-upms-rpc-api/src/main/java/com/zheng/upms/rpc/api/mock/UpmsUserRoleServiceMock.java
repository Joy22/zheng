package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsUserRoleMapper;
import com.zheng.upms.dao.model.UpmsUserRole;
import com.zheng.upms.dao.model.UpmsUserRoleExample;
import com.zheng.upms.rpc.api.UpmsUserRoleService;

/**
* 降级实现UpmsUserRoleService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsUserRoleServiceMock extends BaseServiceMock<UpmsUserRoleMapper, UpmsUserRole, UpmsUserRoleExample> implements UpmsUserRoleService {

}
