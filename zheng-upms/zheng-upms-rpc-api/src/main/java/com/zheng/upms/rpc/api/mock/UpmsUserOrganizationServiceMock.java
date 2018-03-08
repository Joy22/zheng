package com.zheng.upms.rpc.api.mock;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.zheng.upms.dao.model.UpmsUserOrganization;
import com.zheng.upms.dao.model.UpmsUserOrganizationExample;
import com.zheng.upms.rpc.api.UpmsUserOrganizationService;

/**
* 降级实现UpmsUserOrganizationService接口
* @author Joy
* @date 2018/3/2
*/
public class UpmsUserOrganizationServiceMock extends BaseServiceMock<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements UpmsUserOrganizationService {

}
