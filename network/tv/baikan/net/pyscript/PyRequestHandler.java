package tv.baikan.net.pyscript;

import tv.baikan.net.handler.RequestHandler;

/**
 * @Description:所有的Python脚本都继承这个类
 * @Author:HuangShuzhen
 * @Date 2013-4-20
 * @Version V1.0.0
 */
public abstract class PyRequestHandler implements RequestHandler {
	private String doRequest;

	public PyRequestHandler(String doRequest) {
		this.doRequest = doRequest;
	}

	public String getDoRequest() {
		return doRequest;
	}
}
