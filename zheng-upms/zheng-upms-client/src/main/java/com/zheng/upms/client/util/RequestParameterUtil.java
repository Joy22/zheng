package com.zheng.upms.client.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * request参数工具类
 * @author Joy
 * @date 2018/3/7
 */
public class RequestParameterUtil {

    public static String getParameterWithOutCode(HttpServletRequest request){
        StringBuffer backUrl = request.getRequestURL();
        String params = "";
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            if(!"upms_code".equals(entry.getKey()) && !"upms_username".equals(entry.getKey())){
                if("".equals(params)){
                    params = entry.getKey() + "=" + entry.getValue()[0];
                }else {
                    params += "&" + entry.getKey() + "=" + entry.getValue()[0];
                }
            }
        }
        if(StringUtils.isNotBlank(params)){
            backUrl = backUrl.append("?").append(params);
        }
        return backUrl.toString();
    }
}
