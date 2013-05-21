package tv.baikan.net.handler;

import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public interface RequestHandler{
	public ResultMessage doRequest(HttpRequest request) throws Exception;
}
