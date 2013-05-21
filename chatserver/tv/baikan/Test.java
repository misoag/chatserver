package tv.baikan;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Test {
	public static void main(String[] args) throws IOException {
		
		byte [] data = FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\公屏聊天.png"));
		FileUtils.writeStringToFile(new File("c:/txt.txt"), new String(data));
	}
}
