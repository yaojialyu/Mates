#!/usr/bin/env python
# -*- coding: utf-8 -*-

import logging,json,datetime
from tornado.escape import json_encode, json_decode
from models import College

class Chat(object):
    activities = {}

    def add(self, client):
        activityId = client.activityId
        clientSet = self.activities.get(activityId)
        if not clientSet:
            clientSet = self.activities[activityId] = set()
        else:
            #获取同聊天室内的人的信息
            roommateInfo = []
            for client in clientSet:
                roommateInfo.append(json_encode(client.user.__dict__))
            client.write_message({'roommateInfo':roommateInfo})
            #向其它人发送加入的消息
            self.send(activityId, 'add one client!')
        clientSet.add(client)

    def remove(self, client):
        activityId = client.activityId
        clientSet = self.activities.get(activityId)
        clientSet.remove(client)
        if len(clientSet) == 0:
            del self.activities[activityId]
        else:
            self.send(activityId, 'remove one client!')


    def send(self, activityId, message):
        logging.info("sending message:%s waiters in activity:%s" % (message, activityId))
        clientSet = self.activities.get(activityId)
        for client in clientSet:
            try:
                client.write_message(message)
            except:
                logging.error("Error sending message", exc_info=True)

Errors = dict(
        wrongUID = 'wrongUID'
    )

def _to_dict(obj): 
    for item in obj.__dict__.items(): 
        print item
        if item[0][0] is '_': 
            continue 
        if isinstance(item[1], str): 
            yield [item[0], item[1].decode()] 
        else:
            yield item 

def mydumps(obj): 
    if isinstance(obj, models.User):
        getattr(obj,'college')

    if isinstance(obj, list): 
        return json.dumps(map(dict, map(_to_dict, obj)), cls=DatetimeJSONEncoder) 
    else: 
        return json.dumps(dict(_to_dict(obj)), cls=DatetimeJSONEncoder) 

class DatetimeJSONEncoder(json.JSONEncoder): 
    def default(self, o): 
        if isinstance(o, datetime.datetime): 
            return o.strftime('%Y-%m-%d %H:%M:%S') 
        else: 
            return super(DatetimeJSONEncoder, self).default(o)




    