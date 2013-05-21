package tv.baikan.response;

/**
 * @Description:报文编号，只有下行
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class MessageId {
	/**
	 * 空消息
	 */
	public static final int EMPTY = 0;
	/**
	 * 玩家对象
	 */
	public static final int PLAYER = 10001;

	/**
	 * 房间对象
	 */
	public static final int ROOM = 20001;
	/**
	 * 房间列表
	 */
	public static final int ROOM_LIST = 20002;
	/**
	 * 聊天消息对象
	 */
	public static final int CHAT_MESSAGE = 30001;
}
