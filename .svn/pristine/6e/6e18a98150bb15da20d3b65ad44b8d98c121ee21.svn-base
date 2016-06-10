package com.trgis.ticg.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class SessionDao extends AbstractSessionDAO {
	
	
	private String sessionKey="shiro-session:";
	@Autowired
	public RedisTemplate<String, Session> template;
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		template.opsForValue().set((sessionKey+session.getId()).toString(), session);
	}

	@Override
	public void delete(Session session) {
		template.delete(sessionKey+session.getId().toString());

	}

	@Override
	public Collection<Session> getActiveSessions() {
	Set<String> keys=template.keys(sessionKey+"*");
	Set<Session> sessions=new HashSet<Session>();
	 Session s=null;
	if (!keys.isEmpty()) {
		for (String key : keys) {
			s = template.opsForValue().get(key);
			sessions.add(s);
		}
	}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		String sessionId = (String) generateSessionId(session);
		assignSessionId(session, sessionId);
		template.opsForValue().set(sessionKey+sessionId, session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = null;
		if (sessionId!=null) {
			session = template.opsForValue().get(sessionKey+sessionId);
		}
		return session;
	}
}
