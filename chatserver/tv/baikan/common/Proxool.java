package tv.baikan.common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import tv.baikan.Config;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class Proxool {
	public static String mysql_uname;
	public static String mysql_passwd;
	public static String mysql_url;

	static {
		load_config_from_file();
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			Properties info = new Properties();
			info.setProperty("proxool.maximum-connection-count", "10");
			info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
			info.setProperty("user", mysql_uname);
			info.setProperty("password", mysql_passwd);
			String driverClass = "com.mysql.jdbc.Driver";
			String url = "proxool.baikan:" + driverClass + ":" + mysql_url;
			ProxoolFacade.registerConnectionPool(url, info);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
	}

	private static void load_config_from_file() {
		File file = new File(Config.FILE_CONFIT_DB);
		if (file.exists()) {
			Properties p = new Properties();
			try {
				p.load(new FileReader(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			mysql_url = p.getProperty("mysql_url");
			mysql_uname = p.getProperty("mysql_uname");
			mysql_passwd = p.getProperty("mysql_passwd");
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("proxool.baikan");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
