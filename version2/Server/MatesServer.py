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

from models import db, User

class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r'/(?P<userID>\d+)/(?P<roomID>\d+)/', MainHandler),
            (r'/chat/(?P<userID>\d+)/(?P<roomID>\d+)/', ChatSocketHandler),
        ]
        settings = dict(
            #定义template 和 static 文件的路径
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            debug=True,
        )
        tornado.web.Application.__init__(self, handlers, **settings)

class MainHandler(tornado.web.RequestHandler):
    def get(self, userID, roomID):
        self.render("index.html", userID=userID, roomID=roomID)

class ChatSocketHandler(tornado.websocket.WebSocketHandler):

    #chatrooms, each chatroom has a waiter(user) set
    activities = {}

    def allow_draft76(self):
        # for iOS 5.0 Safari
        return True

    def open(self, userID, roomID):
        logging.info("%s has connected to room:%s" %(userID, roomID))
        self.userID = userID
        self.roomID = roomID
        #add current user to its corresponding actitivy (chartroom) 
        waiterSet = ChatSocketHandler.getWaiterSet(roomID)
        waiterSet.add(self)

    def on_close(self):
        logging.info("%s has closed at room:%s" %(self.userID, self.roomID))
        waiterSet = ChatSocketHandler.getWaiterSet(self.roomID)
        waiterSet.remove(self)

    def on_message(self, message):
        logging.info("got message %r", message)
        ChatSocketHandler.send_updates(message, self.roomID)

    @classmethod
    def getWaiterSet(cls, roomID):
        waiterSet = cls.activities.get(roomID)
        if not waiterSet:
            cls.activities[roomID] = set()
        return cls.activities[roomID]

    @classmethod
    def send_updates(cls, message, roomID):
        logging.info("sending message to %d waiters in Room:%s" % (len(cls.activities.get(roomID)), roomID))
        waiterSet = cls.activities.get(roomID)
        for waiter in waiterSet:
            try:
                waiter.write_message(message)
            except:
                logging.error("Error sending message", exc_info=True)

def main():
    tornado.options.parse_command_line()
    app = Application()
    app.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()
