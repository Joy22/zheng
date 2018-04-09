package com.zheng.upms.rpc.api;

import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsRole;
import com.zheng.upms.dao.model.UpmsUser;

import java.util.List;

public class UpmsApiServiceMock implements UpmsApiService {
    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        return null;
    }
}
