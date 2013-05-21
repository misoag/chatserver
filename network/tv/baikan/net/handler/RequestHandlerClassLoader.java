package tv.baikan.net.handler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import tv.baikan.Config;

/**
 * @Description:从Bin中动态的加载RequestHandler。以后需要实现从Jar中动态加载。
 * @Author:HuangShuzhen
 * @Date 2013-4-20
 * @Version V1.0.0
 */
public class RequestHandlerClassLoader extends ClassLoader {

	private Set<Class<RequestHandler>> set = new HashSet<Class<RequestHandler>>();

	private Class<?> findClass(byte[] b) throws ClassNotFoundException {
		return defineClass(null, b, 0, b.length);
	}

	/**
	 * @Description:递归加载RequestHandlerClass
	 * @exception
	 */
	private void realodClasses(File file) {
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String fileName = file.getName();
				if (!fileName.contains("$")) {
					// 防止有内部类
					return true;
				}
				return false;
			}
		});
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				realodClasses(files[i]);
			} else {
				if (files[i].getName().endsWith(".class")) {
					try {
						byte[] data = FileUtils.readFileToByteArray(files[i]);
						Class<RequestHandler> clazz = (Class<RequestHandler>) this.findClass(data);
						set.add(clazz);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	public static Set<Class<RequestHandler>> loadAllHandlers(String packageName) {
		File file = new File(Config.DIR_BIN + "/" + packageName.replace(".", "/"));
		RequestHandlerClassLoader loader = new RequestHandlerClassLoader();
		loader.realodClasses(file);
		return loader.set;
	}

	public static void main(String[] args) {
		Set<Class<RequestHandler>> set = RequestHandlerClassLoader.loadAllHandlers("tv.baikan.handler");
		for (Class<RequestHandler> class1 : set) {
			System.out.println(class1);
		}
	}
}