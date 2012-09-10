#!/usr/bin/env python
# -*- coding: utf-8 -*-

from sqlalchemy import Column, ForeignKey, String, Integer, Float, Text, DateTime, Date, Time
from database import db

class User(db.Model):
    uid = Column(String(16), nullable=False)    #微博id
    screen_name = Column(String(32), nullable=False)   #微博用户名
    description = Column(Text(100))    #微博一句话简介
    gender = Column(String(1), nullable=False, default='n')    #m,f,n(未知)
    profile_image_url = Column(String(100))    #用户头像地址（小头像)
    avatar_large = Column(String(100))    #用户头像地址（大头像)
    college_id = Column(Integer, ForeignKey("college.id", onupdate="CASCADE"))
    join_time = Column(DateTime, nullable=False)   #首次登录时间

class Activity(db.Model):
    title = Column(String(20), nullable=False)    #活动名
    description = Column(Text, nullable=False)    #活动简介
    category_id = Column(Integer, ForeignKey("category.id", onupdate="CASCADE"))    #活动分类
    city_id = Column(Integer, ForeignKey("city.id", onupdate="CASCADE"))  #城市名
    college_id = Column(Integer, ForeignKey("college.id", onupdate="CASCADE"))   
    address = Column(String(50), nullable=False)    #详细地址
    poster_url = Column(String(100), nullable=False)    #活动海报地址（原尺寸）
    poster_small_url = Column(String(100), nullable=False)    #活动海报（小尺寸）
    time_type = Column(Integer, nullable=False)    #活动时间类型——单一时间、连续时间、自定义时间
    lat = Column(Float)    #纬度
    lon = Column(Float)    #经度
    creator_id = Column(Integer, ForeignKey("user.id", onupdate="CASCADE"))  #创建人id
    create_time = Column(DateTime, nullable=False)    #活动创建时间
    

class Category(db.Model):
    name = Column(String(20), nullable=False)   #活动类型名称

class Favourite(db.Model):
    user_id = Column(Integer, ForeignKey("user.id", onupdate="CASCADE"))     #用户id
    activity_id = Column(Integer, ForeignKey("activity.id", onupdate="CASCADE"))    #活动id
    favourited_time = Column(DateTime, nullable=False)   #加星时间

class Time(db.Model):
    activity_id = Column(Integer, ForeignKey("activity.id", onupdate="CASCADE"))    #活动id
    date_begin = Column(Date, nullable=False)    #开始日期
    date_end = Column(Date, nullable=False)    #结束日期
    time_begin = Column(Time, nullable=False)    #开始时间
    time_end = Column(Time, nullable=False)    #结束时间

class College(db.Model):
    name = Column(String(30), nullable=False)    #学校名称
    city_id = Column(Integer, ForeignKey("city.id", onupdate="CASCADE"))
    address = Column(String(60))    #学校地址
    lat = Column(Float)    #纬度
    lon = Column(Float)    #经度

class City(db.Model):
    name = Column(String(50), nullable=False)    #城市名称
    province_id = Column(Integer, ForeignKey("province.id", onupdate="CASCADE"))    #省id

class Province(db.Model):
    name = Column(String(50), nullable=False)    #省名



