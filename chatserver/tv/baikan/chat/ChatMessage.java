package tv.baikan.chat;

import java.util.Date;

/**
 * 聊天消息
 * 
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class ChatMessage {
        /**
         * 房间ID
         */
        private int roomId;
        /**
         * 发送者的ID
         */
        private int uid;
        /**
         * 发送者的用户名
         */
        private String username;
        /**
         * 发送的消息
         */
        private String message;
        /**
         * 是否是私聊
         */
        private boolean isPrivate;
        /**
         * 如果是私聊，目标用户
         */
        private int target;
        /**
         * 如果是私聊，目标用户的用户名
         */
        private String targetUsername;

        /**
         * 消息发送时间
         */
        private Date createTime;

        public ChatMessage(int roomId, int uid, String username, String message, boolean isPrivate, int target, String targetUsername) {
                this.roomId = roomId;
                this.uid = uid;
                this.username = username;
                this.message = message;
                this.isPrivate = isPrivate;
                this.target = target;
                this.targetUsername = targetUsername;
                this.createTime = new Date();
        }

        /**
         * 返回用户的uid
         */
        public int getUid() {
                return uid;
        }

        /**
         * 设置用户的uid
         */
        public void setUid(int uid) {
                this.uid = uid;
        }

        /**
         * 返回房间ID
         */
        public int getRoomId() {
                return roomId;
        }

        /**
         * 设置房间id
         */
        public void setRoomId(int roomId) {
                this.roomId = roomId;
        }

        /**
         * 发送者的username
         */
        public String getUsername() {
                return username;
        }

        /**
         * 设置username
         */
        public void setUsername(String username) {
                this.username = username;
        }

        /**
         * 返回聊天消息
         */
        public String getMessage() {
                return message;
        }

        /**
         * 设置聊天消息
         */
        public void setMessage(String message) {
                this.message = message;
        }

        /**
         * 是否是私聊
         */
        public boolean isPrivate() {
                return isPrivate;
        }

        /**
         * 设置是否是私聊
         */
        public void setPrivate(boolean isPrivate) {
                this.isPrivate = isPrivate;
        }

        /**
         * 私聊的目标用户uid
         */
        public int getTarget() {
                return target;
        }

        /**
         * 设置私聊的目标用户的id
         */
        public void setTarget(int target) {
                this.target = target;
        }

        /**
         * 返回聊天消息创建时间
         */
        public Date getCreateTime() {
                return createTime;
        }

        /**
         * 设置目标用户的username
         */
        public void setTargetUsername(String targetUsername) {
                this.targetUsername = targetUsername;
        }

        /**
         * 返回目标用户的username
         */
        public String getTargetUsername() {
                return targetUsername;
        }

        /**
         * 设置聊天消息创建时间
         */
        public void setCreateTime(Date createTime) {
                this.createTime = createTime;
        }

}
