package tv.baikan.handler;

import tv.baikan.chat.Room;
import tv.baikan.chat.RoomManager;
import tv.baikan.net.handler.DoRequest;
import tv.baikan.net.handler.RequestHandler;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;
import tv.baikan.response.MessageId;

/**
 * 创建一个房间
 * 
 * @author HuangShuzhen
 * @date 2013-4-22
 * @version v1.0.0
 */
@DoRequest("/room/create")
public class RoomCreateHandler implements RequestHandler {

	@Override
	public ResultMessage doRequest(HttpRequest request) {
		String username = request.getParameter("username");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String notice = request.getParameter("notice");
		Room room = RoomManager.getInstance().createRoom(username, title, desc, notice);
		if (room != null) {
			return new ResultMessage(MessageId.ROOM, room);
		} else {
			return new ResultMessage(0, "创建房间失败");
		}
	}
}
