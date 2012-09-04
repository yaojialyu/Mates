package me.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import me.dao.Mysql;
import me.model.User;
import me.model.User.Gender;
import me.model.User.Grade;
import me.model.User.Major;
import org.jivesoftware.smack.XMPPConnection;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.annotation.*;

/**
 * 处理User相关逻辑
 * @author kevin
 *
 */
@Encoding(input = "UTF-8", output = "UTF-8")
public class UserModule {
	

	/**
	 * 注册用户（首次登陆）
	 * 首先检查该用户是否已经存在于数据库，若存在，直接返回该User对象
	 * 若该用户为新用户，则先在openfire处创建用户
	 * 
	 * @param weiboId 微博ID
	 * @param weiboName 微博名
	 * @param avatarUrl 微博头像地址
	 * @return json个格式 User 对象
	 */
	 @Encoding(input = "UTF-8", output = "UTF-8")
	 @At("/user/signup")
	 @Ok("json:{ignoreNull:false}")
	 @AdaptBy(type=PairAdaptor.class)
     public User signup(@Param("weiboId") String weiboId,@Param("weiboName") String weiboName,
    		 @Param("avatarUrl_small") String avatarUrl_small, @Param("avatarUrl_big") String avatarUrl_big) {			 //函数参数就是http post提交的参数，参考：http://code.google.com/p/nutz/wiki/mvc_http_adaptor	
		 
		 if(weiboId==null||weiboName==null||avatarUrl_big==null||avatarUrl_small==null){	//必须传递这些参数过来
			 return null;
		 }
		 
		 Dao dao = new NutDao(Mysql.sds());
		 User user = dao.fetch(User.class,Cnd.where("weiboId","=",weiboId));
		 
		 if(user==null) {
			//在Mates新建用户
			user = new User();
			user.setWeiboId(weiboId);
			user.setWeiboName(weiboName);
			user.setAvatarUrl_big(avatarUrl_big);
			user.setAvatarUrl_small(avatarUrl_small);
			
			//在openfire注册新用户
			Smack smack = new Smack();
			XMPPConnection connection = smack.connect();
			Map<String, String> userAttritubes = new HashMap<String, String>();
			userAttritubes.put("name", weiboName);	//创建用户名(聊天昵称)
			smack.createUser(connection, weiboId, userAttritubes);	
			smack.disconnect();
			
			return dao.insert(user);
			 
		 }else {
			 return user;
		 }
		 									
     }
	 
	 /**
	  * 根据客户端传过来的用户weiboId String(单个或多个)
	  * 例如:单个格式: weiboIds=1010200030
	  * 	多个格式: weiboIds=0202030030,2383993309,2338338383
	  * 
	  * 拿到用户weiboId后,根据weiboID在数据库查找user
	  * @param weiboIds
	  * @return
	  */
	 @Encoding(input = "UTF-8", output = "UTF-8")
	 @At("/user/joinRoom")		
	 @Ok("json")
	 public List<User> getUserAvatarAndUid(@Param("weiboIds") String weiboIds) {
		if(weiboIds==null) {
			return null;
		}
		String[] users = weiboIds.split(",");
		Dao dao = new NutDao(Mysql.sds());
		User user;
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < users.length; i++) {
			 user = dao.fetch(User.class,Cnd.where("weiboId","=",users[i]));
			 if(user!=null){
				user.filter();		//去除其它字段,只保留uid与头像url
				userList.add(user);
			 }
		}
		if (userList.size()==0) {
			return null;
		}
		return userList;
	}
	 
	 
	 
	 
	 
	 /**
	  * 根据uid 返回具体某用户的个人信息
	  * 
	  * @param uid 用户id
	  * @return json个格式 User对象 若查无该人,则返回null
	  */
	 @Encoding(input = "UTF-8", output = "UTF-8")
	 @At("/user/?")		
	 @Ok("json:{ignoreNull:false}")
     public User getUserProfile(long uid) {
		 Dao dao = new NutDao(Mysql.sds());
		 User user = dao.fetch(User.class, Cnd.where("id","=",uid));
		 if(user==null){
			 return null;
		 }
		 return user;
     }
	 
	 
	 /**
	  * 修改用户信息
	  * 要求先验证session
	  * @param uid
	  * @param phone
	  * @return boolean, 是否修改成功
	  */
	 
	 @At("/user/modify/?")
	 @Ok("json:{ignoreNull:false}")
	 @AdaptBy(type=PairAdaptor.class)
	 @Encoding(input = "UTF-8", output = "UTF-8")
	 public boolean modifyUser(long uid, @Param("phone") String phone, @Param("major") Major major, 
			 @Param("qq") String qq, @Param("gender") Gender gender, @Param("grade") Grade grade,
			 HttpServletRequest req) {
		 
		 //TODO：安全机制，session 使用: req.getSession()  !!!先验证session!
		 
		 if(phone==null&&major==null&&qq==null&&gender==null&&grade==null){		//没有提交任何参数
			 return false;
		 }
		 
		 Dao dao = new NutDao(Mysql.sds());
		 User user = dao.fetch(User.class, Cnd.where("id","=",uid));
		 if(phone!=null){
			 user.setPhone(phone);
		 }
		 if(major!=null){
			 user.setMajor(major);
		 }
		 if(qq!=null){
			 user.setQQ(qq);
		 }
		 if(gender!=null){
			 user.setGender(gender);
		 }
		 if(grade!=null){
			 user.setGrade(grade);
		 }
		 
		 if(dao.update(user)==1){
			 return true;
		 }
		 else {
			return false;
		}
		 
	}
	 

}
