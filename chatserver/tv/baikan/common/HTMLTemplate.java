package tv.baikan.common;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import tv.baikan.Config;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HTMLTemplate {

	static {
		Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, Config.DIR_HTML);
		Velocity.setProperty(Velocity.ENCODING_DEFAULT, Config.ENCODING);
		Velocity.setProperty(Velocity.INPUT_ENCODING, Config.ENCODING);
		Velocity.setProperty(Velocity.OUTPUT_ENCODING, Config.ENCODING);
		//不输出日志
		Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		Velocity.init();
	}

	private static String merge(String tpl, VelocityContext ctx) {
		Writer swriter = new StringWriter();
		Template template = Velocity.getTemplate(tpl);
		if (template != null) {
			template.merge(ctx, swriter);
		}
		try {
			swriter.flush();
			return swriter.toString();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				swriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String render(String template, Map<String, Object> map) {
		VelocityContext context = new VelocityContext();
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> entry : set) {
			context.put(entry.getKey(), entry.getValue());
		}
		return merge(template, context);
	}
}
