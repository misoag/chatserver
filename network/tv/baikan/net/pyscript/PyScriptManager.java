package tv.baikan.net.pyscript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.python.util.PythonInterpreter;

import tv.baikan.Config;

/**
 * @Description:Python脚本管理类，可以动态加载Python脚本
 * @Author:HuangShuzhen
 * @Date 2013-4-20
 * @Version V1.0.0
 */
public class PyScriptManager {
	private static List<PyRequestHandler> handlers = new ArrayList<PyRequestHandler>();

	private static void reload() {
		PythonInterpreter interpreter = new PythonInterpreter();
		File files[] = new File(Config.DIR_SCRIPT).listFiles();
		synchronized (handlers) {
			handlers.clear();
			for (File file : files) {
				interpreter.execfile(file.getAbsolutePath());
			}
		}
	}

	public static void add(PyRequestHandler handler) {
		synchronized (handlers) {
			handlers.add(handler);
		}
	}

	public static List<PyRequestHandler> getALLHandlers() {
		reload();
		return handlers;
	}
	
}
