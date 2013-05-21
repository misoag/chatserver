# -*- coding:utf-8 -*-
from tv.baikan.net.pyscript import *
from tv.baikan.net.http import *
from tv.baikan.net.message import *
from tv.baikan.response import *
from tv.baikan.chat import *

#发送聊天消息
class ChatHandler(PyRequestHandler):
	def doRequest(self,request):
		print "聊天"
		roomId = request.getParameter("roomId")
		uid = request.getParameter("uid")
		username = request.getParameter("username")
		message = request.getParameter("message")
		isPrivate = request.getParameter("isPrivate")
		target = request.getParameter("target")
		targetUsername = request.getParameter("targetUsername")
		#print roomId,uid,username,message.encode("utf-8"),isPrivate,target,targetUsername
 		if(roomId and uid and username and message and isPrivate):
 			room = RoomManager.getInstance().getRoomById(int(roomId))
 			sender = room.getUserByUsername(username)
 			if(room and sender and sender.getId() == int(uid)):
 				if(isPrivate=='false'):
 					target = '0'
 					targetUsername=''
 				chatMsg = ChatMessage(int(roomId),int(uid),username,message,isPrivate=='true',int(target),targetUsername);
 				#广播聊天消息
	 			if(chatMsg):room.borderCast(chatMsg)
	 			#返回聊天消息
 				msgList = sender.getMessage(100)
 				return ResultMessage(MessageId.CHAT_MESSAGE,msgList)
 		return None;
 					
#禁言
if __name__ == "__main__":
	chat = ChatHandler("/player/chat")
	PyScriptManager.add(chat)