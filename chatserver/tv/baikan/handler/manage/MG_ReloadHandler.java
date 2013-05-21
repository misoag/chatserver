package tv.baikan.handler.manage;

import tv.baikan.net.handler.DoRequest;
import tv.baikan.net.handler.RequestDispatcher;
import tv.baikan.net.handler.RequestHandler;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
@DoRequest("/reload")
public class MG_ReloadHandler implements RequestHandler {

	@Override
	public ResultMessage doRequest(HttpRequest request) {
		boolean result = true;
		try {
			RequestDispatcher.getInstance().reloadHandlers();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return new ResultMessage(0,result ? "加载成功" : "加载失败").html();
	}

}
