package com.zheng.upms.client.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 强制退出会话过滤器
 * @author Joy
 * @date 2018/3/20
 */
public class UpmsSessionForceLogoutFilter extends AccessControlFilter{

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Session session = getSubject(request, response).getSession(false);
        return session == null || session.getAttribute("FORCE_LOGOUT") == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
