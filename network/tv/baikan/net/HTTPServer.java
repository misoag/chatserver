package tv.baikan.net;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import tv.baikan.Config;
import tv.baikan.common.BKDateFormat;
import tv.baikan.net.codec.HttpProtocolCodecFactory;
import tv.baikan.net.handler.HTTPServerIoHandler;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HTTPServer {

	private static int PROCESSOR = 5;
	private static int PORT = 9090;
	private static int BUFFER_SIZE = 1024 * 1024;
	private static int TIMEOUT = 30;
	private static int IDLE_TIME = 20;

	static {
		File file = new File(Config.FILE_CONFIG_SERVER);
		if (file.exists()) {
			Properties p = new Properties();
			try {
				p.load(new FileReader(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			PROCESSOR = NumberUtils.toInt(p.getProperty("processor"),PROCESSOR);
			PORT = NumberUtils.toInt(p.getProperty("server.port"), PORT);
			BUFFER_SIZE = NumberUtils.toInt(p.getProperty("server.buffer.size"), BUFFER_SIZE);
			TIMEOUT = NumberUtils.toInt(p.getProperty("server.timeout"), TIMEOUT);
			IDLE_TIME = NumberUtils.toInt(p.getProperty("server.idletime"), IDLE_TIME);
		}
	}

	public static void run() throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor(PROCESSOR);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HttpProtocolCodecFactory()));
		acceptor.setHandler(new HTTPServerIoHandler());
		// 相关配置
		SocketSessionConfig config = (SocketSessionConfig) acceptor.getSessionConfig();
		config.setReceiveBufferSize(BUFFER_SIZE);
		config.setReadBufferSize(BUFFER_SIZE);
		config.setSendBufferSize(BUFFER_SIZE);
		config.setKeepAlive(true);
		// config.setSoLinger(0);
		config.setWriteTimeout(TIMEOUT);
		// 读写通道均在20 秒内无任何操作就进入空闲状态。
		config.setIdleTime(IdleStatus.BOTH_IDLE, IDLE_TIME);
		acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
		acceptor.bind();
		System.out.println("["+BKDateFormat.current()+"]HTTPServer listening on " + PORT);
	}
}
