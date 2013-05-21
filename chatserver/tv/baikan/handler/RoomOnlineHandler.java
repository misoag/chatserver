package tv.baikan.handler;

import tv.baikan.chat.Player;
import tv.baikan.chat.PlayerManager;
import tv.baikan.chat.Room;
import tv.baikan.chat.RoomManager;
import tv.baikan.net.handler.DoRequest;
import tv.baikan.net.handler.RequestHandler;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;
import tv.baikan.response.MessageId;

/**
 * 上线房间
 * @author HuangShuzhen
 * @date 2013-4-22
 * @version v1.0.0
 */
@DoRequest("/room/online")
public class RoomOnlineHandler implements RequestHandler{

	@Override
	public ResultMessage doRequest(HttpRequest request) throws Exception {
		String username = request.getParameter("username");
		int roomId = request.getIntParameter("roomId");
		Player player = PlayerManager.getInstance().getPlayerByUsername(username);
		if(player!=null){
			Room room = RoomManager.getInstance().onLineRoom(roomId);
			if(room != null){
				return new ResultMessage(MessageId.ROOM, room);
			}else{
				return new ResultMessage(0, "房间不存在");
			}
		}else{
			return new ResultMessage(0, "房间上线失败：房主必须在线，并且房间必须由房主上线");
		}
	}

}
