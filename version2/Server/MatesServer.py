#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Server side for Mates.
handle chat info using Websocket.
"""

import logging
import tornado.escape
import tornado.ioloop
import tornado.options
import tornado.web
import tornado.websocket
import os.path
import settings
from tornado.options import options
from database import db
from models import User
from util import Chat

class Application(tornado.web.Application):
    def __init__(self):
        self.chat = Chat()
        handlers = [
            (r'/activities/(?P<api>[a-z]+)', ActivityAPIHandler),
            (r'/(?P<userID>\d+)/(?P<activityId>\d+)/', MainHandler),
            (r'/chat/(?P<userID>\d+)/(?P<activityId>\d+)/', ChatSocketHandler),
        ]
        settings = dict(
            #定义template 和 static 文件的路径
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            debug=True,
        )
        tornado.web.Application.__init__(self, handlers, **settings)

class MainHandler(tornado.web.RequestHandler):
    def get(self, userID, activityId):
        self.render("index.html", userID=userID, activityId=activityId)

class ChatSocketHandler(tornado.websocket.WebSocketHandler):
    def allow_draft76(self):
        return True

    def open(self, userID, activityId):
        logging.info("%s has connected to activity:%s" %(userID, activityId))
        self.userID = userID
        self.activityId = activityId
        self.application.chat.add(self)
        
    def on_close(self):
        logging.info("%s has closed at activity:%s" %(self.userID, self.activityId))
        self.application.chat.remove(self)

    def on_message(self, message):
        logging.info("got message %r", message)
        self.application.chat.send(self.activityId, message)

class ActivityAPIHandler(tornado.web.RequestHandler):
    def post(self,api):
        ###### show near by activities #####
        if api == 'near':
            pass

        ###### show home activities #####
        elif api == 'home':
            pass

        ###### show my favourited activities #####
        elif api == 'favourites':
            pass

        ###### show single activity info #####
        elif api == 'show':
            pass


def main():
    # 创建数据库
    # db.create_db()
    tornado.options.parse_command_line()
    app = Application()
    app.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()
