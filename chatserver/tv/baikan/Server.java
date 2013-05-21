package tv.baikan;

import java.io.IOException;

import tv.baikan.common.BKDateFormat;
import tv.baikan.net.HTTPServer;
import tv.baikan.net.handler.RequestDispatcher;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
public class Server {

	public static void start() {
		try {
			long current = System.currentTimeMillis();
			//初始化RquestDispatcher
			RequestDispatcher.getInstance();
			//启动Http服务器
			HTTPServer.run();
			System.out.println("["+BKDateFormat.current()+"]Start server cost "+(System.currentTimeMillis() - current)+" millisecond");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stop() {
		//发送关闭服务器通知
		//保存玩家数据
		//保存服务器数据
		//关闭服务器
		System.exit(0);
	}

	public static void main(String[] args) {
		start();
	}
}
