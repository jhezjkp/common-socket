package com.game.socket.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.CodecService;

/**
 * 编码器
 * 
 * @author 依形掠影
 *
 */
public class ServerProtocolEncoder extends ProtocolEncoderAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ServerProtocolEncoder.class);

	@Override
	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out) throws Exception {
		if (obj instanceof Message) {
			Message msg = (Message) obj;
			IoBuffer buf = IoBuffer.allocate(32);
			buf.setAutoExpand(true);
			buf.setAutoShrink(true);
			// 包长占位
			buf.putInt(0);
			// 协议号
			buf.putInt(msg.getId());
			// 写数据
			CodecService.getInstance().message2Bytes(buf, msg);
			// 数据包长度
			int len = buf.limit() - 4;
			buf.putInt(len);
			buf.rewind();
			out.write(buf);
			out.flush();
		} else {
			logger.error("xxxxxx message cannot be encoded: class={}", obj.getClass());
		}

	}

}
