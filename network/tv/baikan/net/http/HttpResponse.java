package tv.baikan.net.http;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HttpResponse {
	private static StringBuffer header = new StringBuffer();
	static {
		String current_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").format(new Date());
		header.append("HTTP/1.1 200 OK \r\n");
		header.append("Server:ChatServer/1.0 \r\n");
		header.append("Cache-Control:private \r\n");
		header.append("Content-Type:text/html; charset=utf-8 \r\n");
		header.append("Connection:keep-alive \r\n");
		header.append("Keep-Alive:200 \r\n");
		header.append("Date:" + current_time + " \r\n");
		header.append("Last-Modified:" + current_time + " \r\n");
	}
	private String body;

	public HttpResponse(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		String h = header.toString();
		return h + "\r\n" + body + "\r\n";
	}
}
