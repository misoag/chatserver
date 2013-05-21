package tv.baikan.chat;

/**
 * 主播管理
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class ActorManager {
        private ActorManager() {
        }

        public static ActorManager getInstance() {
                return SingletonHolder.instance;
        }

        private static class SingletonHolder {
                private static ActorManager instance = new ActorManager();
        }
}
