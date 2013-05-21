package tv.baikan.net.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tv.baikan.common.BKDateFormat;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;
import tv.baikan.net.pyscript.PyRequestHandler;
import tv.baikan.net.pyscript.PyScriptManager;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class RequestDispatcher {

	private Map<String, RequestHandler> handlers = new HashMap<String, RequestHandler>();

	private RequestDispatcher() {
		try {
			reloadHandlers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据URI找到对应的Handler
	 * 
	 * @param request
	 * @return
	 */
	private RequestHandler getRequestHandler(HttpRequest request) {
		String uri = request.getUri();
		return handlers.get(uri);
	}

	/**
	 * @Description:重新加载Handler void
	 * @exception
	 */
	public void reloadHandlers() throws Exception {
		// 加载PyRequestHandler
		List<PyRequestHandler> pyHandlers = PyScriptManager.getALLHandlers();
		for (PyRequestHandler handler : pyHandlers) {
			String path = handler.getDoRequest();
			if (path != null && !"".equals(path) && handler != null) {
				addRequestHandler(path, handler);
			}
		}
		// 从ClassPath中加载RequetHandler
		Set<Class<RequestHandler>> set = RequestHandlerClassLoader.loadAllHandlers("tv.baikan.handler");
		for (Class<RequestHandler> clazz : set) {
			DoRequest annotation = clazz.getAnnotation(DoRequest.class);
			if (annotation != null) {
				String path = annotation.value();
				RequestHandler handler = clazz.newInstance();
				addRequestHandler(path, handler);
			}
		}
		System.out.println("["+BKDateFormat.current()+"]RequestHandlerSize:"+this.handlers.size());
	}

	private synchronized void addRequestHandler(String path, RequestHandler handler) {
		handlers.put(path, handler);
	}

	/**
	 * 分发请求
	 * 
	 * @param request
	 * @return
	 */
	public ResultMessage dispatch(HttpRequest request) {
		RequestHandler handler = getRequestHandler(request);
		if (handler != null) {
			try {
				ResultMessage message = handler.doRequest(request);
				if(message !=null){
					return message;
				}else{
					return new ResultMessage(0, "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultMessage(0, "500 Server Error");
			}
		}
		return new ResultMessage(0, "Not Found RequestHandler");
	}

	public static RequestDispatcher getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static RequestDispatcher instance = new RequestDispatcher();
	}

}
