package tv.baikan.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HttpProtocolCodecFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder = new HttpResponseEncoder();
	private ProtocolDecoder decoder = new HttpRequestDecoder();

	public HttpProtocolCodecFactory() {
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return this.decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return this.encoder;
	}

}
