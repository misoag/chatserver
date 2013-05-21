# -*- coding:utf-8 -*-
from tv.baikan.net.pyscript import *
from tv.baikan.net.http import *
from tv.baikan.net.message import *
from tv.baikan.response import *
from tv.baikan.chat import *

#每日任务：打卡签到
class PunchCardHandler(PyRequestHandler):
	def doRequest(self, request):
		m = request.getMethod().encode("utf-8")
		s = "打卡成功，赠送2000金币"
		x = s+m
		return HTMLMessage(x.decode("utf-8"))
		
if __name__ == "__main__":
	handler = PunchCardHandler("/punchcard")
	PyScriptManager.add(handler)
