package tv.baikan;

import java.nio.charset.Charset;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class Config {
	public static String ENCODING = "UTF-8";
	public static Charset CHARSET = Charset.forName(ENCODING);

	public static String DIR_HOME = System.getProperty("user.dir");
	public static String DIR_HTML = DIR_HOME + "/html";
	public static String DIR_LIB = DIR_HOME + "/lib";
	public static String DIR_BIN = DIR_HOME + "/bin";
	public static String DIR_SCRIPT = DIR_HOME + "/pyscript";
	public static String FILE_CONFIT_DB = DIR_HOME + "/config/db.properties";
	public static String FILE_CONFIG_SERVER = DIR_HOME + "/config/server.properties";
	static {
		DIR_HTML = DIR_HTML.replaceAll("\\\\", "/");
		DIR_LIB = DIR_LIB.replaceAll("\\\\", "/");
		DIR_BIN = DIR_BIN.replaceAll("\\\\", "/");
		DIR_SCRIPT = DIR_SCRIPT.replaceAll("\\\\", "/");
		FILE_CONFIT_DB = FILE_CONFIT_DB.replaceAll("\\\\", "/");
		FILE_CONFIG_SERVER = FILE_CONFIG_SERVER.replaceAll("\\\\", "/");
	}
}
