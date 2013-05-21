package tv.baikan.net.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;

import tv.baikan.Config;

/**
 * 不能完整的HTTP协议解析
 * 
 * @Description:解析：请求行、消息报头、请求正文
 * @Author:Huangshuzhen
 * @Date 2013-4-18 上午7:59:40
 * @Version V1.0.0
 */
public class HttpRequest {

	private String method;
	private String uri;
	private String version;
	private byte[] body;

	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();

	public HttpRequest() {
		this.method = "GET";
		this.uri = "/";
		this.version = "HTTP/1.1";
	}

	public HttpRequest(byte[] data) {
		try {
			init1(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init1(byte[] data) throws Exception {
		int CRLFIndex = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] == HTTP.CR && data[i + 1] == HTTP.LF) {
				byte temp[] = Arrays.copyOfRange(data, CRLFIndex, i);
				if (CRLFIndex == 0) {
					parseRequestLine(new String(temp));
				} else {
					parseRequestHeaderLine(new String(temp));
				}
				CRLFIndex = i += 2;
				if (data[i] == HTTP.CR && data[i + 1] == HTTP.LF) {
					int len = getContentLength();
					CRLFIndex+=2;
					body = Arrays.copyOfRange(data, CRLFIndex, CRLFIndex+len);
//					System.out.println("ContentLength:"+len);
//					System.out.println("Body:"+body.length);
//					System.out.println("Data:"+data.length);
					//FileUtils.writeByteArrayToFile(new File("c:/"+System.currentTimeMillis()+".png"), body);
					parseRequestBody(body);
					break;
				}
			}

		}
	}

	private void init2(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			byte read = -1;
			// 解析请求行
			while ((read = dis.readByte()) != -1) {
				baos.write(read);
				if (read == HTTP.LF) {
					baos.flush();
					byte[] line = baos.toByteArray();
					parseRequestLine(new String(line, Config.CHARSET));
					baos.reset();
					break;
				}
			}
			// 消息报头
			while ((read = dis.readByte()) != -1) {
				baos.write(read);
				if (read == HTTP.LF) {
					baos.flush();
					byte[] line = baos.toByteArray();
					if (line[0] == HTTP.CR && line[1] == HTTP.LF) {
						baos.reset();
						break;
					} else {
						parseRequestHeaderLine(new String(line, Config.CHARSET));
					}
					baos.reset();
				}
			}
			// 解析报文body
			int cnt_len = getContentLength();
			if (cnt_len > 0) {
				baos.write(data, data.length - cnt_len, cnt_len);
				baos.flush();
				body = baos.toByteArray();
				// ==write to file
				System.out.println("ContentLength:"+getContentLength());
				System.out.println("BodyLength:"+body.length);
				 FileUtils.writeByteArrayToFile(new File("c:/fdsafdsa.png"), body);
				parseRequestBody(body);
			}

			baos.close();
			dis.close();
			bais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:解析HTTP协议的请求行
	 * @exception
	 */
	private void parseRequestLine(String requestLine) {
		String[] segment = requestLine.split("\\s");
		if (segment.length == 3) {
			this.method = segment[0];
			this.uri = segment[1];
			this.version = segment[2];
			// 解析uri中的参数
			int index = uri.indexOf("?");
			if (index > 0) {
				String paramString = uri.substring(index + 1);
				parseParameter(paramString);
				this.uri = uri.substring(0, index);
			}
		}

	}

	/**
	 * @Description:解析HTTP协议的Header
	 * @exception
	 */
	private void parseRequestHeaderLine(String header) {
		if (header.contains(":")) {
			String segment[] = header.split("\\:");
			if (segment.length == 2) {
				headers.put(segment[0].trim().toLowerCase(), segment[1].trim());
			}
		}
	}

	/**
	 * @Description:解析HTTP协议的Body void
	 * @exception
	 */
	private void parseRequestBody(byte[] body) {
		String contentType = getHeader("Content-Type");
		if (contentType != null) {
			if (contentType.equals(HTTP.Req.CNTTYPE_X_FORM)) {
				// 普通的Form表单
				parseParameter(new String(body, Config.CHARSET));
			} else if (contentType.equals(HTTP.Req.CNTTYPE_M_FORM)) {
				// 上传文件，如果是浏览器上传的话这里需要处理一下
				// System.out.println(new String(body,HttpConst.defaultCharset));
			}
		}
	}

	private void parseParameter(String params) {
		try {
			params = URLDecoder.decode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] kvString = params.split("\\&");
		for (String kv : kvString) {
			String param[] = kv.split("\\=");
			if (param.length == 2) {
				parameters.put(param[0], param[1]);
			}
		}
	}

	public String getParameter(String key) {
		return parameters.get(key);
	}

	public int getIntParameter(String key) {
		String valueStr = parameters.get(key);
		valueStr = (valueStr == null ? "0" : valueStr);
		return NumberUtils.toInt(valueStr);
	}

	public String getHeader(String key) {
		return headers.get(key.toLowerCase());
	}

	public int getContentLength() {
		String cnt_len_str = getHeader("Content-Length");
		return NumberUtils.toInt(cnt_len_str == null ? "0" : cnt_len_str);
	}

	public String getMethod() {
		return method;
	}

	public String getVersion() {
		return version;
	}

	public String getUri() {
		return uri;
	}
	
	public byte[] getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "";
	}
}
