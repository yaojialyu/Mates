#!/usr/bin/env python
# -*- coding: utf-8 -*-

from database import SQLAlchemy
from sqlalchemy import Column, String
from tornado.options import options

db = SQLAlchemy("mysql://%s:%s@%s:%s/%s" % (options.dbuser, options.dbpass,
	options.dbhost, options.dbport, options.dbname), pool_recycle=3600)

class User(db.Model):
    username = Column(String(16), unique=True, nullable=False)
    password = Column(String(30), nullable=False)


