package tv.baikan;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class FileUpload {

        public static void main(String[] args) {
                try {
                        byte[] data = FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\x.gif"));
                        String result = upload(data);
                        System.out.println(result);
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public static String upload(byte[] body) {
                try {
                        URL url = new URL("http://192.168.1.199:9090/player/avatar");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.setRequestProperty("Content-type", "multipart/form-data");

                        OutputStream os = con.getOutputStream();
                        os.write(body);
                        os.write(0x0);
                        os.flush();
                        InputStream is = con.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String str = "";
                        StringBuffer sb = new StringBuffer();
                        while ((str = br.readLine()) != null) {
                                sb.append(str);
                        }
                        os.close();
                        is.close();
                        return sb.toString();
                }
                catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

}
