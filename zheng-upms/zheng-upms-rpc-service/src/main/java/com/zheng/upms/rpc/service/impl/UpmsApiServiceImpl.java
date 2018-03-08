package com.zheng.upms.rpc.service.impl;

import com.zheng.upms.dao.mapper.UpmsUserMapper;
import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsRole;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.dao.model.UpmsUserExample;
import com.zheng.upms.rpc.api.UpmsApiService;
import com.zheng.upms.rpc.mapper.UpmsApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UpmsApiService实现
 * @author Joy
 * @date 2018/3/5
 */
@Service
@Transactional
public class UpmsApiServiceImpl implements UpmsApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    private UpmsUserMapper upmsUserMapper;

    @Autowired
    private UpmsApiMapper upmsApiMapper;

    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> upmsUsers = upmsUserMapper.selectByExample(upmsUserExample);
        if(null != upmsUsers && upmsUsers.size() > 0){
            return upmsUsers.get(0);
        }
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        if(userAvailable(upmsUserId)){
            return upmsApiMapper.selectUpmsRoleByUpmsUserId(upmsUserId);
        }
        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        if(userAvailable(upmsUserId)){
            return upmsApiMapper.selectUpmsPermissionByUpmsUserId(upmsUserId);
        }
        return null;
    }

    /**
     * 检查用户是否可以（存在并且非锁定状态）
     * @param upmsUserId
     * @return
     */
    private boolean userAvailable(Integer upmsUserId){
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || 1 == upmsUser.getLocked()) {
            LOGGER.debug("UpmsUser[{}] is not available.", upmsUserId);
            return false;
        }
        return true;
    }
}
