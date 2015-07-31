package com.game.socket.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.game.demo.message.request.ReqLoginMessage;

/**
 * IO处理器
 * 
 * @author 依形掠影
 *
 */
public class ServerProtocolHandler extends IoHandlerAdapter {

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		if (message instanceof ReqLoginMessage) {
			ReqLoginMessage msg = (ReqLoginMessage) message;
			System.out.println(msg.getUserName() + "\t" + msg.getPassword());
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

}
