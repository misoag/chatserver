package tv.baikan.net.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import tv.baikan.net.http.HttpResponse;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
class HttpResponseEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession session) throws Exception {

	}

	@Override
	public void encode(IoSession session, Object object, ProtocolEncoderOutput out) throws Exception {
		HttpResponse response = (HttpResponse) object;
		byte [] data = response.toString().getBytes(Charset.forName("utf-8"));
		IoBuffer buffer = IoBuffer.allocate(data.length, true);
		buffer.put(data);
		buffer.flip();
		out.write(buffer);
	}

}
