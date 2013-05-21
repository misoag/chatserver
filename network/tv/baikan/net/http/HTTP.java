package tv.baikan.net.http;


/**
 * @Description:这里定义HTTP相关的常量
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HTTP {
	public static final byte CR = 13;
	public static final byte LF = 10;

	public static class Resp {
		public static final int CODE_OK = 200;
		public static final int CODE_NOT_FOND = 404;
		public static final int CODE_SERVER_ERROR = 500;
	}

	public static class Req {
		public static final String CNTTYPE_TEXT_HTML = "text/html";
		public static final String CNTTYPE_TEXT_JSON = "text/json";
		public static final String CNTTYPE_X_FORM = "application/x-www-form-urlencoded";
		public static final String CNTTYPE_M_FORM = "multipart/form-data";
	}
}
