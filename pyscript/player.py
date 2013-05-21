# -*- coding:utf-8 -*-
from tv.baikan.common import *
from tv.baikan.net.pyscript import *
from tv.baikan.net.http import *
from tv.baikan.net.message import *
from tv.baikan.response import *
from tv.baikan.chat import *

# 玩家注册
class PlayerRegisterHandler(PyRequestHandler):
	def doRequest(self, request):
		print "玩家注册"
		username = request.getParameter("username")
		password = request.getParameter("password")
		nickname = request.getParameter("nickname")
 		if(username and password and nickname):
 			user = PlayerManager.getInstance().register(username, password, nickname);
 			if user:
 				return ResultMessage(MessageId.PLAYER, user)
 			else:
 				return ResultMessage(0, u"注册失败，用户名不可用！")
 		else:
		 	return ResultMessage(0, u"注册失败")

# 玩家登陆
class PlayerLoginHandler(PyRequestHandler):
	def doRequest(self, request):
		print "玩家登陆"
		username = request.getParameter("username")
		password = request.getParameter("password")
		if(username and password):
			user = PlayerManager.getInstance().login(username, password)
			if(user):
				return ResultMessage(MessageId.PLAYER, user)
			else:
				return ResultMessage(0, u"登陆失败，没有找到该玩家！")
		else:
			return ResultMessage(0, u"登陆失败")

# 玩家退出
class PlayerExitHandler(PyRequestHandler):
	def doRequest(self, request):
		print "玩家退出"
		username = request.getParameter("username")
		password = request.getParameter("password")
		if(username and password):
			user = PlayerManager.getInstance().exit(username, password)
			if(user):
				# 从所有房间中删除玩家
				RoomManager.getInstance().removeUser(user);
				return ResultMessage(MessageId.PLAYER, user)
			else:
				return ResultMessage(0, u"玩家已经退出")
		else:
			return ResultMessage(0, u"玩家退出失败")
		
# 进入房间
class PlayerEnterRoom(PyRequestHandler):
	def doRequest(self, request):
		print "进入房间"
		username = request.getParameter("username")
		roomId = request.getParameter("roomId")
		print username, roomId
		if(username and roomId):
			user = PlayerManager.getInstance().getPlayerByUsername(username)
			room = RoomManager.getInstance().getRoomById(int(roomId))
			if(user and room):
				if(room.getUserByUsername(username) == None):
					room.addUser(user)
					msg = u"欢迎" + user.getNickname() + u"进入房间"
					room.borderCast(msg)
					# 返回聊天消息
 					msgList = user.getMessage(10)
 				 	return ResultMessage(MessageId.CHAT_MESSAGE, msgList)
				else:
					return None
			else:
				return ResultMessage(0, u"进入房间失败")
		else:
			return ResultMessage(0, u"进入房间失败")
		
# 退出房间
class PlayerExitRoom(PyRequestHandler):
	def doRequest(self, request):
		print "退出房间"
		username = request.getParameter("username")
		roomId = request.getParameter("roomId")
		if(username and roomId):
			user = PlayerManager.getInstance().getPlayerByUsername(username)
			room = RoomManager.getInstance().getRoomById(int(roomId))
			if(user and room):
				room.removeUser(user)
				return ResultMessage(1, u"退出房间成功")
			else:
				return ResultMessage(0, u"退出房间失败")
		else:
			return ResultMessage(0, u"退出房间失败")

# 获取玩家信息
class PlayerInfo(PyRequestHandler):
	def doRequest(self, req):
		username = req.getParameter("username")
		if(username):
			player = PlayerManager.getInstance().getPlayerByUsername(username)
			if(player):
				return ResultMessage(MessageId.PLAYER, player)
			else:
				return ResultMessage(0, u'玩家不在线')
		else:
			return ResultMessage(0, u'没有找到玩家')
			
# 编辑玩家信息
class PlayerEdit(PyRequestHandler):
	def doRequest(self, req):
		uid = req.getParameter("uid")
		username = req.getParameter("username")
		nickname = req.getParameter("nickname")
		sex = req.getParameter("sex")
		if(uid and username and nickname and sex):
			player = PlayerManager.getInstance().getPlayerByUsername(username)
			if(player):
				player.setNickname(nickname)
				player.setSex(int(sex))
				player.syncToDB()
				return ResultMessage(MessageId.PLAYER, player)
			else:
				return ResultMessage(0, u'玩家不在线')
		else:
			return ResultMessage(0, u'修改失败')
		
# 上传用户头像
class PlayerAvatar(PyRequestHandler):
	def doRequest(self, req):
		print "上传头像"
		username = req.getParameter("username")
		player = PlayerManager.getInstance().getPlayerByUsername(username)
		if(player):
			avatar = ImageManager.uploadAvatar(req.body)
			player.setAvatar(avatar)
			player.syncToDB();
			return ResultMessage(1, avatar)
		else:
			return ResultMessage(0, u'上传头像失败')
					
if __name__ == "__main__":
	reg_handler = PlayerRegisterHandler("/player/register")
	PyScriptManager.add(reg_handler)
	login_handler = PlayerLoginHandler("/player/login")
	PyScriptManager.add(login_handler)
	exit_handler = PlayerExitHandler("/player/exit")
	PyScriptManager.add(exit_handler)
	enter_room = PlayerEnterRoom("/player/enterroom")
	PyScriptManager.add(enter_room)
	exit_room = PlayerExitRoom("/player/exitroom")
	PyScriptManager.add(exit_room)
	
	player_info = PlayerInfo("/player/info")
	PyScriptManager.add(player_info)
	player_edit = PlayerEdit("/player/edit")
	PyScriptManager.add(player_edit)
	player_avatar = PlayerAvatar("/player/avatar")
	PyScriptManager.add(player_avatar)
	
	
