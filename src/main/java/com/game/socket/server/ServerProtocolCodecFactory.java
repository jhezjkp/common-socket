package com.game.socket.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 编解码工厂
 * 
 * @author 依形掠影
 *
 */
public class ServerProtocolCodecFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	public ServerProtocolCodecFactory() {
		encoder = new ServerProtocolEncoder();
		decoder = new ServerProtocolDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

}
