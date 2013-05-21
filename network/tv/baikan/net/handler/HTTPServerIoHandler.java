package tv.baikan.net.handler;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.http.HttpResponse;
import tv.baikan.net.message.ResultMessage;

import com.alibaba.fastjson.JSON;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class HTTPServerIoHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		HttpRequest request = (HttpRequest) message;
		ResultMessage resultMessage = RequestDispatcher.getInstance().dispatch(request);
		String text = "";
		if (!resultMessage.isHtml()) {
			text = JSON.toJSONString(resultMessage);
		} else {
			text = resultMessage.getBody().toString();
		}
		HttpResponse response = new HttpResponse(text);
		session.write(response).addListener(IoFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		session.close(true);
	}
}