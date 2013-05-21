package tv.baikan.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tv.baikan.common.BasicDao;

/**
 * 房间管理
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class RoomManager {
        /**
         * 在线房间列表
         */
        private Map<Integer, Room> onLineRooms = new ConcurrentHashMap<Integer, Room>();

        /**
         * 根据OwnerId从数据库中查询房间列表
         */
        public List<Room> getRoomByOwnerFromDB(int owner) {
                String sql = "SELECT * FROM room WHERE `owner` = ?";
                List<Room> list = new BasicDao().queryList(Room.class, sql, owner);
                return list;
        }

        /**
         * 根据RoomName从数据库中查询房间
         */
        public Room getRoomByTitleFromDB(String roomTitle) {
                String sql = "SELECT * FROM room WHERE `title` = ?";
                Room room = new BasicDao().querySingal(Room.class, sql, roomTitle);
                return room;
        }

        /**
         * 根据RoomId从数据库中加载一个房间
         */
        public Room getRoomByIdFromDB(int roomId) {
                String sql = "SELECT * FROM room WHERE id = ?";
                Room room = new BasicDao().querySingal(Room.class, sql, roomId);
                return room;
        }

        /**
         * 根据RoomId从在线房间列表中获取
         */
        public Room getRoomById(int roomId) {
                return onLineRooms.get(roomId);
        }

        /**
         * 创建一个房间
         */
        public Room createRoom(String owner, String title, String desc, String notice) {
                Player player = PlayerManager.getInstance().getPlayerByUsername(owner);
                if (player != null) {
                        Room room = new Room(player.getId(), title, desc, notice);
                        room.setNewRoom(true);
                        room.syncToDB();
                        room = getRoomByTitleFromDB(title);
                        return room;
                }
                return null;
        }

        /**
         * 上线一个房间
         */
        public Room onLineRoom(int roomId) {
                Room room = onLineRooms.get(roomId);
                if (room == null) {
                        room = getRoomByIdFromDB(roomId);
                        if (room != null) {
                                room.setOnLine(true);
                                onLineRooms.put(roomId, room);
                        }
                }
                return room;
        }

        /**
         * 下线一个房间
         */
        public Room offLineroom(int roomId) {
                Room room = onLineRooms.get(roomId);
                if (room != null) {
                        room.syncToDB();
                        room.offline();
                        onLineRooms.remove(room.getId());
                }
                return room;
        }

        /**
         * 删除玩家（从所有房间中都删除）
         */
        public void removeUser(User user) {
                for (Room room : onLineRooms.values()) {
                        room.removeUser(user);
                }
        }

        /**
         * 返回在线的房间列表
         */
        public List<Room> getOnLineRoomList() {
                List<Room> list = new ArrayList<Room>();
                for (Room room : onLineRooms.values()) {
                        list.add(room);
                }
                return list;
        }

        /**
         * 返回在线房间数量
         */
        public int getOnLineRoomSize() {
                return onLineRooms.size();
        }

        private RoomManager() {

        }

        public static RoomManager getInstance() {
                return SingletonHolder.instance;
        }

        private static class SingletonHolder {
                private static RoomManager instance = new RoomManager();
        }
}
