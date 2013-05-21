package tv.baikan.handler.manage;

import java.util.HashMap;
import java.util.List;

import tv.baikan.chat.PlayerManager;
import tv.baikan.chat.Room;
import tv.baikan.chat.RoomManager;
import tv.baikan.common.HTMLTemplate;
import tv.baikan.net.handler.DoRequest;
import tv.baikan.net.handler.RequestHandler;
import tv.baikan.net.http.HttpRequest;
import tv.baikan.net.message.ResultMessage;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
@DoRequest("/serverinfo")
public class MG_ServerHandler implements RequestHandler {

	@Override
	public ResultMessage doRequest(HttpRequest request) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//服务器
		Runtime runtime = Runtime.getRuntime();
		map.put("availableProcessors", runtime.availableProcessors());
		map.put("maxMemory", runtime.maxMemory());
		map.put("freeMemory", runtime.freeMemory());
		//玩家
		PlayerManager playerManger = PlayerManager.getInstance();
		map.put("playerSize",playerManger.getPlayerSize());
		map.put("playerList", playerManger.getPlayerList());
		//房间
		List<Room> roomList = RoomManager.getInstance().getOnLineRoomList();
		map.put("roomList", roomList);
		map.put("roomSize", roomList.size());
		
		String html = HTMLTemplate.render("ServerInfo.html", map);
		return new ResultMessage(0,html).html();
	}

}
