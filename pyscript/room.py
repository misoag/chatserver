# -*- coding:utf-8 -*-
from tv.baikan.net.pyscript import *
from tv.baikan.net.http import *
from tv.baikan.net.message import *
from tv.baikan.response import *
from tv.baikan.chat import *

#从房间中踢出玩家
class KickOutPlayer(PyRequestHandler):
	def doRequest(self,request):
		print "踢出玩家"
		#房间号
		roomId = request.getParameter("roomId")
		#踢人者
		username = request.getParameter("username")
		#被踢者
		target = request.getParameter("targetUsername")
		print roomId,username,target
		#print roomId,uid,username,message.encode("utf-8"),isPrivate,target,targetUsername
		if(roomId and username and target):
 			room = RoomManager.getInstance().getRoomById(int(roomId))
 			target = room.getUserByUsername(target)
 			if(room and target):
 				room.removeUser(target)
 				return ResultMessage(1,u"踢出用户成功")
 		 	else:
 		 		return ResultMessage(0,u'踢出用户失败')
 		return None;
 					
if __name__ == "__main__":
	kick = KickOutPlayer("/room/kick")
	PyScriptManager.add(kick)