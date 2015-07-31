package com.game.socket.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.socket.Message;
import com.game.socket.codec.CodecService;

/**
 * 解码器
 * 
 * @author 依形掠影
 *
 */
public class ServerProtocolDecoder extends ProtocolDecoderAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ServerProtocolDecoder.class);

	@Override
	public void decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// 先判断是否满4个字节(Integer的长度)
		while (buf.remaining() > 4) {
			// 判断是否够一个数据包的数据
			int len = buf.getInt();
			if (buf.remaining() < len) {
				buf.rewind();
				return;
			} else {
				// 将字节数据转换为协议消息
				int id = buf.getInt();
				Message msg = CodecService.getInstance().bytes2Message(id, buf);
				if (msg != null) {
					out.write(msg);
				}
			}
		}

	}

}
