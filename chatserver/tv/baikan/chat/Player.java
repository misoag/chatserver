package tv.baikan.chat;

import tv.baikan.common.BasicDao;

/**
 * 玩家
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class Player extends User {
        // 关注的主播列表，是否可以踢人，是否是VIP，防踢

        /**
         * 是否可以送花
         */
        boolean canFlower = true;
        /**
         * 是否可以私聊
         */
        boolean canPrivate = true;

        /**
         * 是否是VIP用户
         */
        boolean vip;

        public Player() {
        }

        public Player(String username, String password, String nickname) {
                super(username, password, nickname);
        }

        /**
         * 是否可以送花
         */
        public boolean isCanFlower() {
                return canFlower;
        }

        /**
         * 开启或关闭送花
         */
        public void setCanFlower(boolean canFlower) {
                this.canFlower = canFlower;
        }

        /**
         * 是否可以私聊
         */
        public boolean isCanPrivate() {
                return canPrivate;
        }

        /**
         * 开启或关闭私聊
         */
        public void setCanPrivate(boolean canPrivate) {
                this.canPrivate = canPrivate;
        }

        /**
         * 是否是VIP用户
         */
        public boolean isVip() {
                return vip;
        }

        /**
         * 设置为VIP用户
         */
        public void setVip(boolean vip) {
                this.vip = vip;
        }

        /*
         * (non-Javadoc)
         * 
         * @see tv.baikan.chat.User#syncToDB()
         */
        public void syncToDB() {
                BasicDao dao = new BasicDao();
                if (this.isNewPlayer()) {
                        String sql = "INSERT INTO player (username,`password`,nickname,registerTime,loginTime,lastRequestTime) VALUES(?,?,?,?,?,?);";
                        dao.insert(sql, username, password, nickname, registerTime, loginTime, lastRequestTime);
                } else {
                        String sql = "UPDATE player SET `password`=?,nickname=?,sex=?,birthday=?,city=?,avatar=?,`level`=?,money=?,canFlower=?,canPrivate=?,`vip`=?,loginTime=?,lastRequestTime=? WHERE username = ?;";
                        dao.update(sql, password, nickname, sex, birthday, city, avatar, level, money, canFlower, canPrivate, vip, loginTime, lastRequestTime, username);
                }
        }
}
