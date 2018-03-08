package com.zheng.upms.common.constant;

import com.zheng.common.base.BaseResult;

/**
 * upms系统返回结果
 * @author Joy
 * @date 2018/3/3
 */
public class UpmsResult extends BaseResult{

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data){
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
    }
}
