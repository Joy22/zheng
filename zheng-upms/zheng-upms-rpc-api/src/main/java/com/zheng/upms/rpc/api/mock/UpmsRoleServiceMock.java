package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsRoleMapper;
import com.zheng.upms.dao.model.UpmsRole;
import com.zheng.upms.dao.model.UpmsRoleExample;
import com.zheng.upms.rpc.api.UpmsRoleService;

/**
* 降级实现UpmsRoleService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsRoleServiceMock extends BaseServiceMock<UpmsRoleMapper, UpmsRole, UpmsRoleExample> implements UpmsRoleService {

}
