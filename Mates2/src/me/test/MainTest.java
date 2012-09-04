package me.test;

import java.lang.annotation.Annotation;

import me.dao.Mysql;
import me.model.Activity;
import me.model.User;
import me.model.User.Gender;
import me.model.User.Grade;
import me.model.User.Major;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.mvc.annotation.Chain;

public class MainTest {
	
	public static void main(String[] args){
		/*User u = new User();
		u.setId(1);
		u.setWeiboId("222");
		u.setWeiboName("elvis");
		u.setMajor(Major.CST);
		u.setGender(Gender.Male);
		u.setGrade(Grade.Year_Three);
		Dao dao = new NutDao(Mysql.sds());*/
		//dao.insert(u);  //插入
		/* System.out.println(dao.query(User.class, 
				Cnd.where("weiboName","=","elvis"),null));//查询  */
		//dao.update(u);
		
		String string = "aaa,bbbs";
		String[] a = string.split(",");
		
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		
	}
}
