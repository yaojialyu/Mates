package hk.edu.uic.mates.model.db;

import java.util.ArrayList;

import hk.edu.uic.mates.model.vo.JoinHistory;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class JoinHistoryDAO {
	
	private Context context;
	
	public JoinHistoryDAO(){
		
	}
	
	public JoinHistoryDAO(Context context){
		this.context = context;
	}
	
	/**
	 * 增加聊天室访问历史
	 * 
	 * @param roomId 被访问的房间id
	 * @param uid 访问者uid（即matesId）
	 * @return
	 */
	public boolean addJoinHistory(int roomId, long uid) {
		boolean isAdd = false;
		String sql_2 = "insert into JoinHistory(roomId, times, userId) values('"+roomId+"', '"+1+"', '"+uid+"')";
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			dB.execSQL(sql_2);
			isAdd = true; 
			dB.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return isAdd;
	}

	/**
	 * 更新聊天室访问历史
	 * times次数+1
	 * 
	 * @param roomId 被访问的房间id
	 * @param uid 访问者uid（即matesId）
	 * @return
	 */
	public boolean updateJoinHistory(int roomId, long uid) {
		boolean isUp = false;
		JoinHistory joinHistory = new JoinHistory();
		int times = 0;
		String sql_1 = "select times from JoinHistory where roomId = '"+roomId+"' and userId = '"+uid+"'";
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			Cursor cursor = dB.rawQuery(sql_1, new String[]{});
			if(cursor.moveToNext()){
				joinHistory.setTimes(cursor.getInt(cursor.getColumnIndex("times")));
				times = joinHistory.getTimes();
				times = times + 1;
				String sql = "update JoinHistory set times = '"+times+"', lastJoinTime = CURRENT_TIMESTAMP where roomId = '"+roomId+"' and userId = '"+uid+"'";
				dB.execSQL(sql);
				
				isUp = true; 
			}
			cursor.close();
			dB.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return isUp;
	}
	
	/**
	 * 判断该聊天室的访问记录是否存在
	 * 
	 * @param roomId 被访问的房间id
	 * @param uid 访问者uid（matesId）
	 * @return
	 */
	public boolean hasJoinBefore(int roomId, long uid) {
		boolean joinBefore = false;
		String sql = "select * from JoinHistory where roomId = '"+roomId+"' and userId = '"+uid+"'";
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			Cursor cursor = dB.rawQuery(sql, new String[]{});
			if(cursor.moveToNext()){
				joinBefore = true;
				
			}else{
				joinBefore = false;
			}
			cursor.close();
			dB.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return joinBefore;
	}
	
	/**
	 * 获取前十间常去的聊天室
	 * 排序标准：
	 * 1. 次数多者优先
	 * 2. 次数一样的情况下，最近访问者优先
	 * （如room1和room2都进入过2次，但room1是昨天去的，room2是前天去的，这时候取room1）
	 * 
	 * @param uid 访问者id(matesId)
	 * @return roomIdAry 前10间常去聊天室
	 */
	public ArrayList<Integer> getTopTen(long uid) {
		//int[] topTen = new int[10];
		//int[] topTenIndex = {}; 
		ArrayList<Integer> ai = new ArrayList<Integer>();
		JoinHistory joinHistory = new JoinHistory();
		String sql = "select * from JoinHistory where userId = '"+uid+"' order by times DESC ,lastJoinTime DESC LIMIT 0 , 10";
		try{
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			Cursor cursor = dB.rawQuery(sql, new String[]{});
			while(cursor.moveToNext()){
					joinHistory.setRoomId(cursor.getInt(cursor.getColumnIndex("roomId")));
					joinHistory.setTimes(cursor.getInt(cursor.getColumnIndex("times")));
					//System.out.println(joinHistory.getRoomId());
					ai.add(joinHistory.getRoomId());
					//System.out.println(ai);
			}
			cursor.close();
			dB.close();
			return ai;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
