package tv.baikan.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tv.baikan.common.BasicDao;
import tv.baikan.net.message.ResultMessage;
import tv.baikan.response.MessageId;

/**
 * 房间
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class Room {

        /**
         * 房间号
         */
        int id;
        /**
         * 房主ID
         */
        int owner;
        /**
         * 主播ID
         */
        int actor;
        /**
         * 房间标题
         */
        String title;
        /**
         * 房间描述
         */
        String desc;

        /**
         * 房间公告
         */
        String notice;

        /**
         * 是否是刚创建的房间
         */
        boolean newRoom;

        /**
         * 是否在线
         */
        boolean onLine;

        /**
         * 房间中的用户,包含主播和玩家
         */
        private Map<String, User> users = new ConcurrentHashMap<String, User>();

        /**
         * 禁言玩家列表
         */
        private Map<String, User> shutupMap = new ConcurrentHashMap<String, User>();

        public Room() {

        }

        public Room(int owner, String title, String desc, String notice) {
                this.owner = owner;
                this.title = title;
                this.desc = desc;
                this.notice = notice;
        }

        public int getOwner() {
                return owner;
        }

        public void setOwner(int owner) {
                this.owner = owner;
        }

        public int getActor() {
                return actor;
        }

        public void setActor(int actor) {
                this.actor = actor;
        }

        /**
         * 获得房间Id
         */
        public int getId() {
                return id;
        }

        /**
         * 设置房间Id
         */
        public void setId(int id) {
                this.id = id;
        }

        /**
         * 获得房间标题
         */
        public String getTitle() {
                return title;
        }

        /**
         * 设置房间标题
         */
        public void setTitle(String title) {
                this.title = title;
        }

        /**
         * 获得房间描述
         */
        public String getDesc() {
                return desc;
        }

        /**
         * 设置房间描述
         */
        public void setDesc(String desc) {
                this.desc = desc;
        }

        /**
         * 获得房间公告
         */
        public String getNotice() {
                return notice;
        }

        /**
         * 是否是新创建的房间
         */
        public boolean isNewRoom() {
                return newRoom;
        }

        /**
         * 设置为新创建的房间
         */
        public void setNewRoom(boolean isNewRoom) {
                this.newRoom = isNewRoom;
        }

        /**
         * 房间是否在线
         */
        public boolean isOnLine() {
                return onLine;
        }

        /**
         * 设置房间是否在线
         */
        public void setOnLine(boolean onLine) {
                this.onLine = onLine;
        }

        /**
         * 设置房间公告
         */
        public void setNotice(String notice) {
                this.notice = notice;
        }

        /**
         * 广播消息，只有可能是聊天消息吗？
         */
        public void borderCast(ChatMessage message) {
                ResultMessage rm = new ResultMessage(MessageId.CHAT_MESSAGE, message);
                // 被禁言用户
                if (shutupMap.containsKey(message.getUsername())) {
                        User user = shutupMap.get(message.getUsername());
                        message.setUid(0);
                        message.setMessage("您已被禁言");
                        user.addMessage(rm);
                        return;
                }
                // 私聊
                if (message.isPrivate()) {
                        User target = users.get(message.getTarget());
                        if (target != null) {
                                target.addMessage(rm);
                        }
                } else {
                        for (User user : users.values()) {
                                user.addMessage(rm);
                        }
                }
        }

        /**
         * 广播消息
         */
        public void borderCast(String message) {
                borderCast(new ChatMessage(this.id, 0, "", message, false, 0, ""));
        }

        /**
         * 返回用户列表
         */
        public List<User> getUserList() {
                List<User> userList = new ArrayList<User>();
                for (User user : users.values()) {
                        userList.add(user);
                }
                return userList;
        }

        /**
         * 返回当前房间中用户的数量
         */
        public int getUserSize() {
                return users.size();
        }

        /**
         * 房间下线
         */
        public void offline() {
                // 发送广播给请用户
                borderCast(new ChatMessage(this.id, 0, "", "房间即将关闭", false, 0, ""));
                users.clear();
        }

        /**
         * 添加用户
         */
        public void addUser(User user) {
                users.put(user.getUsername(), user);
        }

        /**
         * 删除用户
         */
        public void removeUser(User user) {
                users.remove(user.getUsername());
        }

        /**
         * 根据用户名查找用户
         */
        public User getUserByUsername(String username) {
                if (username != null) {
                        return users.get(username);
                }
                return null;
        }

        /**
         * 禁言
         */
        public User shutup(String username) {
                if (username != null) {
                        User user = users.get(username);
                        if (user != null) {
                                shutupMap.put(username, user);
                                return user;
                        }
                }
                return null;
        }

        /**
         * 解除禁言
         */
        public User releaseShutup(String username) {
                if (username != null) {
                        return shutupMap.remove(username);
                }
                return null;
        }

        /**
         * 同步Room到数据库，如果是新房间则创建一条记录
         */
        public void syncToDB() {
                BasicDao dao = new BasicDao();
                if (this.isNewRoom()) {
                        String sql = "INSERT INTO room(`owner`,actor,title,`desc`,notice) VALUES(?,?,?,?,?)";
                        dao.insert(sql, owner, actor, title, desc, notice);
                } else {
                        String sql = "UPDATE room SET `owner`=?,actor=?,title=?,`desc`=?,notice=? WHERE id = ?;";
                        dao.update(sql, owner, actor, title, desc, notice, id);
                }
        }

}
