package tv.baikan.chat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import tv.baikan.net.message.ResultMessage;

/**
 * 抽象用户类：Player和Actor的父类
 * 
 * @author:HuangShuzhen 2013-4-21
 * @version v1.0.0
 */
public abstract class User {
        /**
         * 用户ID
         */
        private int id;
        /**
         * 用户名
         */
        protected String username;
        /**
         * 密码
         */
        protected String password;
        /**
         * 昵称
         */
        protected String nickname;
        /**
         * 性别：0-女1-男
         */
        protected int sex;

        /**
         * 生日
         */
        protected Date birthday;
        /**
         * 所在城市
         */
        protected String city;
        /**
         * 头像URL
         */
        protected String avatar;
        /**
         * 等级
         */
        protected int level;
        /**
         * 金币
         */
        protected int money;

        /**
         * 是否是新用户
         */
        protected boolean newPlayer;

        /**
         * 注册时间
         */
        protected Date registerTime;

        /**
         * 用户登陆时间
         */
        protected Date loginTime = new Date();
        /**
         * 最后发起请求的时间
         */
        protected Date lastRequestTime = new Date();

        /**
         * 房间编号
         */
        protected int roomId;

        /**
         * 消息队列
         */
        private Deque<ResultMessage> messageQueue = new ArrayDeque<ResultMessage>();

        public User() {
        }

        public User(String username, String password, String nickname) {
                this.username = username;
                this.password = password;
                this.nickname = nickname;
        }

        /**
         * 获取所在的房间ID
         */
        public int getRoomId() {
                return roomId;
        }

        /**
         * 设置房间ID
         */
        public void setRoomId(int roomId) {
                this.roomId = roomId;
        }

        /**
         * 获取用户名
         */
        public String getUsername() {
                return username;
        }

        /**
         * 获取用户ID
         */
        public int getId() {
                return id;
        }

        /**
         * 设置用户ID
         */
        public void setId(int id) {
                this.id = id;
        }

        /**
         * 获取用户的生日
         */
        public Date getBirthday() {
                return birthday;
        }

        /**
         * 设置用户的生日
         */
        public void setBirthday(Date birthday) {
                this.birthday = birthday;
        }

        /**
         * 获取用户所在城市
         */
        public String getCity() {
                return city;
        }

        /**
         * 设置用户所在城市
         */
        public void setCity(String city) {
                this.city = city;
        }

        /**
         * 设置用户名
         */
        public void setUsername(String username) {
                this.username = username;
        }

        /**
         * 获取性别：0-女1-男
         */
        public int getSex() {
                return sex;
        }

        /**
         * 设置性别：0-女1-男
         */
        public void setSex(int sex) {
                this.sex = sex;
        }

        /**
         * 获取用户昵称
         */
        public String getNickname() {
                return nickname;
        }

        /**
         * 设置用户昵称
         */
        public void setNickname(String nickname) {
                this.nickname = nickname;
        }

        /**
         * 获取用户的密码
         */
        public String getPassword() {
                return password;
        }

        /**
         * 返回用户的密码
         */
        public void setPassword(String password) {
                this.password = password;
        }

        /**
         * 获取登录时间
         */
        public Date getLoginTime() {
                return loginTime;
        }

        /**
         * 获得用户头像
         */
        public String getAvatar() {
                return avatar;
        }

        /**
         * 设置用户头像
         */
        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }

        /**
         * 获得等级
         */
        public int getLevel() {
                return level;
        }

        /**
         * 设置等级
         */
        public void setLevel(int level) {
                this.level = level;
        }

        /**
         * 获取金币
         */
        public int getMoney() {
                return money;
        }

        /**
         * 设置金币
         */
        public void setMoney(int money) {
                this.money = money;
        }

        /**
         * 是否是新注册用户
         */
        public boolean isNewPlayer() {
                return newPlayer;
        }

        /**
         * 设置：是否新注册用户
         */
        public void setNewPlayer(boolean newPlayer) {
                this.newPlayer = newPlayer;
        }

        /**
         * 返回登录时间
         */
        public void setLoginTime(Date loginTime) {
                this.loginTime = loginTime;
        }

        /**
         * 获取最后一次请求的时间
         */
        public Date getLastRequestTime() {
                return lastRequestTime;
        }

        /**
         * 返回最后一次请求的时间
         */
        public void setLastRequestTime(Date lastRequestTime) {
                this.lastRequestTime = lastRequestTime;
        }

        /**
         * 返回用户注册时间
         */
        public Date getRegisterTime() {
                return registerTime;
        }

        /**
         * 设置用户注册时间
         */
        public void setRegisterTime(Date registerTime) {
                this.registerTime = registerTime;
        }

        /**
         * 插入一条Amessage到消息队列
         */
        public void addMessage(ResultMessage msg) {
                synchronized (messageQueue) {
                        messageQueue.addLast(msg);
                }
        }

        /**
         * 返回一个Amessage列表，如果MessageQueue中的消息数量小于size，则全部返回。
         */
        public List<ResultMessage> getMessage(int size) {
                if (size > 0) {
                        synchronized (messageQueue) {
                                size = Math.min(messageQueue.size(), size);
                                List<ResultMessage> messageList = new ArrayList<ResultMessage>(size);
                                for (int i = 0; i < size; i++) {
                                        ResultMessage msg = messageQueue.removeFirst();
                                        messageList.add(msg);
                                }
                                return messageList;
                        }
                }
                return new ArrayList<ResultMessage>(0);
        }

        /**
         * 返回用户队列消息数量
         */
        public int getQueueSize() {
                return messageQueue.size();
        }

        /**
         * 清空消息队列
         */
        public void clearMessageQueue() {
                synchronized (messageQueue) {
                        messageQueue.clear();
                }
        }

        /**
         * 同步用户信息到数据库，服务器每隔（默认：5分钟）就会执行一次该方法，也可以直接调用该方法立即同步数据到数据库。
         */
        protected abstract void syncToDB();

}
