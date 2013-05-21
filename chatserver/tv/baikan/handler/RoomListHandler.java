package tv.baikan.handler;

import java.util.List;

import tv.baikan.chat.Room;
import tv.baikan.chat.RoomManager;
import tv.baikan.net.handler.DoRequest;
import tv.baikan.net.handler.RequestHandler;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;
import tv.baikan.response.MessageId;

/**
 * 返回所有在线的房间
 * @author HuangShuzhen
 * @date 2013-4-22
 * @version v1.0.0
 */
@DoRequest("/room/list")
public class RoomListHandler implements RequestHandler{

	@Override
	public ResultMessage doRequest(HttpRequest request) throws Exception {
		List<Room> list = RoomManager.getInstance().getOnLineRoomList();
		return new ResultMessage(MessageId.ROOM_LIST, list);
	}

}
