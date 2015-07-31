package com.game;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.server.ServerProtocolCodecFactory;
import com.game.socket.server.ServerProtocolHandler;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		IoAcceptor acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerProtocolCodecFactory()));
		acceptor.setHandler(new ServerProtocolHandler());

		try {
			acceptor.bind(new InetSocketAddress(8000));
			logger.info("===== server started...");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
