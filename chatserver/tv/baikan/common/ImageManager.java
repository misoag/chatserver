package tv.baikan.common;

import java.util.UUID;

import org.python.antlr.PythonParser.else_clause_return;

public class ImageManager {
        /**
         * 上传用户头像
         */
        public static String uploadAvatar(byte[] data) {
                String file = "/avatar/" + generateRandomName() + getExt(data);
                boolean result = uploadFile(file, data);
                if ( result ) {
                        return "http://bkchat.b0.upaiyun.com" + file;
                }
                else {
                        return "";
                }
        }

        /**
         * 或得图片的扩展名 （根据图片特征码判断）
         */
        public static String getExt(byte data[]) {
                if ( data != null && data.length > 3 ) {
                        int v1 = (data[0] & 0xff) << 16;
                        int v2 = (data[1] & 0xff) << 8;
                        int v3 = (data[2] & 0xff);
                        int head = v1 | v2 | v3;
                        if ( head == 0xffd8ff ) {
                                return ".jpg";
                        }
                        else if ( head == 0x89504e ) {
                                return ".png";
                        }
                        else if ( head == 0x474946 ) {
                                return ".gif";
                        }
                }
                return ".unknown";
        }

        /**
         * 上传图片
         */
        public static boolean uploadFile(String file, byte[] data) {
                try {
                        return new UpYun("bkchat", "baikan", "n0n0n0way").writeFile(file, data, true);
                }
                catch (Exception e) {
                        e.printStackTrace();

                }
                return false;
        }

        /**
         * 删除图片
         */
        public static boolean deleteFile(String file) {
                try {
                        return new UpYun("bkchat", "baikan", "n0n0n0way").deleteFile(file);
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
                return false;
        }

        /**
         * 生成随机的用户名
         */
        private static String generateRandomName() {
                return UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }
}
