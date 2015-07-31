package com.game.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.game.demo.message.request.ReqLoginMessage;
import com.game.socket.server.ServerProtocolCodecFactory;

public class ClientMain {

	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerProtocolCodecFactory()));
		connector.setHandler(new ClientIoHandler());

		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", 8000));
		cf.awaitUninterruptibly();

		IoSession session = cf.getSession();
		session.write(new ReqLoginMessage("demo", "demo"));

		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

}
