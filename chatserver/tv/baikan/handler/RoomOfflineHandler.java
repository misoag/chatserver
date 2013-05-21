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
 * 下线房间
 * @author HuangShuzhen
 * @date 2013-4-22
 * @version v1.0.0
 */
@DoRequest("/room/offline")
public class RoomOfflineHandler implements RequestHandler{

	@Override
	public ResultMessage doRequest(HttpRequest request) throws Exception {
		String owner = request.getParameter("username");
		int roomId = request.getIntParameter("roomId");
		Player player = PlayerManager.getInstance().getPlayerByUsername(owner);
		Room room = RoomManager.getInstance().getRoomById(roomId);
		if(room !=null && player !=null && room.getOwner() == player.getId()){
			RoomManager.getInstance().offLineroom(roomId);
			return new ResultMessage(MessageId.ROOM, room);
		}else{
			return new ResultMessage(0, "房间下线失败");
		}
		
	}

}
