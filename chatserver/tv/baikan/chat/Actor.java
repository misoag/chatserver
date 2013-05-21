package tv.baikan.chat;

/**
 * 主播
 * 
 * @author HuangShuzhen
 * @date 2013-4-21
 * @version v1.0.0
 */
public class Actor extends User {

        /**
         * 一段个人简介录像地址URL
         */
        String video;

        /**
         * 个人履历
         */
        String resume;

        /**
         * 获取个人简介录像地址URL
         */
        public String getVideo() {
                return video;
        }

        /**
         * 设置个人简介录像地址URL
         */
        public void setVideo(String video) {
                this.video = video;
        }

        /**
         * 获取主播的履历
         */
        public String getResume() {
                return resume;
        }

        /**
         * 设置主播的履历
         */
        public void setResume(String resume) {
                this.resume = resume;
        }

        /*
         * (non-Javadoc)
         * 
         * @see tv.baikan.chatserver.User#syncToDB()
         */
        @Override
        protected void syncToDB() {

        }

}