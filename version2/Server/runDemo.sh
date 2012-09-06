#!/bin/sh
nohup python ./websocket/chatdemo.py --port=80 > ./chatdemo.log 2>&1 &

