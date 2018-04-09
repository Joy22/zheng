package com.zheng.upms.rpc.api;

import com.zheng.common.base.BaseServiceMock;
import com.zheng.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.zheng.upms.dao.model.UpmsUserOrganization;
import com.zheng.upms.dao.model.UpmsUserOrganizationExample;

/**
* 降级实现UpmsUserOrganizationService接口
* @author Joy
* @date 2018/4/9
*/
public class UpmsUserOrganizationServiceMock extends BaseServiceMock<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements UpmsUserOrganizationService {

}
