package tv.baikan.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tv.baikan.common.BasicDao;

/**
 * 玩家管理
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class PlayerManager {
        private Map<String, Player> players = new ConcurrentHashMap<String, Player>();

        /**
         * 用户注册。成功：返回Player，失败：返回Null。
         */
        public Player register(String username, String password, String nickname) {
                if (username == null || password == null || nickname == null) {
                        return null;
                }

                Player player = getPlayerByUsernameFormDB(username);
                if (player == null) {
                        player = new Player(username, password, nickname);
                        player.setRegisterTime(new Date());
                        player.setNewPlayer(true);
                        // 将新用户保存到数据库
                        player.syncToDB();
                        // 从数据库中重新加载一次
                        player = getPlayerByUsernameFormDB(username);
                        player.setNewPlayer(true);
                        synchronized (players) {
                                players.put(username, player);
                        }
                        return player;
                }
                return null;
        }

        /**
         * 用户登陆，成功：返回Player，失败：返回null，用户名密码错误。
         */
        public Player login(String username, String password) {
                if (username == null || password == null) {
                        return null;
                }
                Player player = players.get(username);
                if (player == null) {
                        player = getPlayerByUsernameFormDB(username);
                        if (player != null && player.getPassword().equals(password)) {
                                synchronized (players) {
                                        players.put(username, player);
                                }
                                return player;
                        } else {
                                return null;
                        }
                }
                return player;
        }

        /**
         * 用户退出，成功：返回Player
         */
        public Player exit(String username, String password) {
                if (username == null || password == null) {
                        return null;
                }
                Player player = players.get(username);
                if (player != null && player.getPassword().equals(password)) {
                        player.syncToDB();
                        players.remove(player.getUsername());
                }
                return player;
        }

        /**
         * 在房间中根据UserName查找用户
         */
        public Player getPlayerByUsername(String username) {
                if (username != null) {
                        return players.get(username);
                }
                return null;
        }

        /**
         * 获得总用户数
         */
        public int getPlayerSize() {
                return players.size();
        }

        /**
         * 获得用户列表
         */
        public List<Player> getPlayerList() {
                List<Player> list = new ArrayList<Player>();
                for (Player player : players.values()) {
                        list.add(player);
                }
                return list;
        }

        /**
         * 根据UserName从数据库中查询用户
         */
        public Player getPlayerByUsernameFormDB(String username) {
                BasicDao dao = new BasicDao();
                Player player = dao.querySingal(Player.class, "select * from player WHERE username = ?", username);
                if (player != null) {
                        return player;
                }
                return null;
        }

        /**
         * 根据NickName从数据库中查询用户
         */
        public Player getPlayerByNicknameFromDB(String nickname) {
                if (nickname != null) {
                        BasicDao dao = new BasicDao();
                        Player player = dao.querySingal(Player.class, "select * from player WHERE nickname = ?", nickname);
                        if (player != null) {
                                return player;
                        }
                }
                return null;
        }

        /**
         * 根据userId从数据库中查询用户
         */
        public Player getPlayerByIdFromDB(int uid) {
                BasicDao dao = new BasicDao();
                Player player = dao.querySingal(Player.class, "select * from player WHERE id = ?", uid);
                if (player != null) {
                        return player;
                }
                return null;
        }

        private PlayerManager() {
        }

        public static PlayerManager getInstance() {
                return SingletonHolder.instance;
        }

        private static class SingletonHolder {
                private static PlayerManager instance = new PlayerManager();
        }
}
