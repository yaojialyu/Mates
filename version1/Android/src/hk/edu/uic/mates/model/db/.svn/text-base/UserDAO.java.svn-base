package hk.edu.uic.mates.model.db;

import hk.edu.uic.mates.model.vo.User;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class UserDAO extends AndroidTestCase{
	
	private Context context;
	
	public UserDAO(){
		
	}
	
	public UserDAO(Context context){
		this.context = context;
	}

	/**
	 * 更新用户头像本地路径
	 * 
	 * @param uid 用户id
	 * @param avatarPath_big 用户大头像头像路径
	 * @param avatarPath_small 用户小头像路径
	 * @return boolean
	 */
	public boolean updateAvatarUrl(int uid, String avatarPath_big, String avatarPath_small) {
		boolean isUp = false;
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			String sql = "update User set avatarPathBig = '"+avatarPath_big+"', avatarPathSmall = '"+avatarPath_small+"' where id = '"+uid+"'";
			dB.execSQL(sql);
			dB.close();
			isUp = true;
		}catch(SQLException e){
			isUp = false;
			e.printStackTrace();
		}
		return isUp;
	}
	
	/**
	 * 增加用户记录
	 * 
	 * @param user 用户
	 * @return
	 */
	public boolean addUser(User user) {
		boolean isAdd = false;
	//	System.out.println("cao");
		try{
			//System.out.println("diao");
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			//System.out.println("nima");
			dB.execSQL("insert into User(matesId, weiboId, weiboName, avatarPathBig, avatarPathSmall) values('"+user.getMatesId()+"', '"+user.getWeiboId()+"','"+user.getWeiboName()+"', '"+user.getAvatarPath_big()+"', '"+user.getAvatarPath_small()+"')");                                       
		    dB.close();
			isAdd = true;
		}catch(SQLException e){
			//System.out.println("gun");
			isAdd = false;
			e.printStackTrace();
		}
		return isAdd;
	}
	
	/**
	 * 获得用户
	 * 
	 * @param uid 用户id
	 * @return user 用户实体
	 */
	public User getUser(int uid) {
		User user = new User();
		ArrayList<User> array = new ArrayList<User>();
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).getReadableDatabase();
			Cursor cursor = dB.rawQuery("select * from User where id = '"+uid+"'", new String[]{});
			if(cursor.moveToNext()){
				user.setId(cursor.getInt(cursor.getColumnIndex("id")));
				user.setMatesId(cursor.getInt(cursor.getColumnIndex("matesId")));
				user.setWeiboId(cursor.getString(cursor.getColumnIndex("weiboId")));
				user.setWeiboName(cursor.getString(cursor.getColumnIndex("weiboName")));
				user.setAvatarPath_big(cursor.getString(cursor.getColumnIndex("avatarPathBig")));
				user.setAvatarPath_small(cursor.getString(cursor.getColumnIndex("avatarPathSmall")));
				array.add(user);
			}
			cursor.close();
			dB.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user;
	}
}
