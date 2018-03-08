package com.zheng.upms.client.shiro.session;

import com.zheng.common.util.RedisUtil;
import com.zheng.upms.client.util.SerializableUtil;
import com.zheng.upms.common.constant.UpmsConstant;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * 基于redis的sessionDao，缓存共享session
 * @author Joy
 * @date 2018/3/2
 */
public class UpmsSessionDao extends CachingSessionDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsSessionDao.class);

    /**
     * 会话key
     */
    private final static String ZHENG_UPMS_SHIRO_SESSION_ID = "zheng-upms-shiro-session-id";

    /**
     * 全局会话key
     */
    private final static String ZHENG_UPMS_SERVER_SESSION_ID = "zheng-upms-server-session-id";

    /**
     * 全局会话列表
     */
    private final static String ZHENG_UPMS_SERVER_SESSION_IDS = "zheng-upms-server-session-ids";

    /**
     * code key
     */
    private final static String ZHENG_UPMS_SERVER_CODE = "zheng-upms-server-code";

    /**
     * 局部会话key
     */
    private final static String ZHENG_UPMS_CLIENT_SESSION_ID = "zheng-upms-client-session-id";

    /**
     * 单点同一个code所有局部会话key
     */
    private final static String ZHENG_UPMS_CLIENT_SESSION_IDS = "zheng-upms-client-session-ids";

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()){
            return;
        }
        // 更新session最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if(null != cacheUpmsSession){
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(buildShiroSessionKey(session.getId()), SerializableUtil.serialize(session), getSessionTimeOut(session));
        // TODO 更新ZHENG_UPMS_SHIRO_SESSION_ID、ZHENG_UPMS_SERVER_CODE过期时间
        LOGGER.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String upmsType = ObjectUtils.toString(session.getAttribute(UpmsConstant.UPMS_TYPE));
        if("client".equals(upmsType)){
            // 删除局部会话和同一code注册的局部会话
            String code = RedisUtil.get(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            jedis.srem(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code, sessionId);
            jedis.close();
        }

        if("server".equals(upmsType)){
            // 当前全局会话code
            String code = RedisUtil.get(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            RedisUtil.remove(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            RedisUtil.remove(ZHENG_UPMS_SERVER_CODE + "_" + sessionId);
            // 清除所有局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(ZHENG_UPMS_CLIENT_SESSION_IDS+"_" + code);
            for(String clientSessionId : clientSessionIds){
                jedis.del(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
                jedis.srem(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code, clientSessionId);
            }
            LOGGER.debug("当前code={}，对应的注册系统个数：{}个", code, jedis.scard(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code));
            // 维护会话id列表，提供会话分页管理
            RedisUtil.lrem(ZHENG_UPMS_SERVER_SESSION_IDS, 1, sessionId);
        }
        // 删除session
        RedisUtil.remove(buildShiroSessionKey(sessionId));
        LOGGER.debug("doDelete >>>>> sessionId={}", sessionId);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(buildShiroSessionKey(sessionId), SerializableUtil.serialize(session), getSessionTimeOut(session));
        LOGGER.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        LOGGER.debug("doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    /**
     * 获取会话列表
     * @param offset
     * @param limit
     * @return
     */
    public Map getActiveSessions(int offset, int limit){
        Map<String, Object> sessions = new HashMap<String, Object>(2);
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        Long total = jedis.llen(ZHENG_UPMS_SERVER_SESSION_IDS);
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(ZHENG_UPMS_SERVER_SESSION_IDS, offset, (offset + limit -1));
        List<Session> rows = new ArrayList<Session>();
        for(String id : ids){
            String session = RedisUtil.get(buildShiroSessionKey(id));
            if(null == session){
                RedisUtil.lrem(ZHENG_UPMS_SERVER_SESSION_IDS, 1, id);
                total = total -1;
                continue;
            }
            rows.add(SerializableUtil.deserialize(session));
        }
        jedis.close();
        sessions.put("total", total);
        sessions.put("rows", rows);
        return sessions;
    }

    /**
     * 强制退出
     * @param ids
     * @return
     */
    public int forceout(String ids){
        String[] sessionIds = ids.split(",");
        for(String sessionId : sessionIds){
            String session = RedisUtil.get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(buildShiroSessionKey(sessionId), SerializableUtil.serialize(upmsSession), getSessionTimeOut(upmsSession));
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus){
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if(null == session){
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(buildShiroSessionKey(sessionId), SerializableUtil.serialize(session), getSessionTimeOut(session));
    }

    private String buildShiroSessionKey(Serializable sessionId){
        return ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId;
    }

    private int getSessionTimeOut(Session session){
        return (int) (session.getTimeout()/1000);
    }
}
