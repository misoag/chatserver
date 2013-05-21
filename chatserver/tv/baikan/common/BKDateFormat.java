package tv.baikan.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.python.antlr.ast.Str;

public class BKDateFormat {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String defaultFormat(Date date){
		return dateFormat.format(date);
	}
	public static String current(){
		return defaultFormat(new Date());
	}
}
