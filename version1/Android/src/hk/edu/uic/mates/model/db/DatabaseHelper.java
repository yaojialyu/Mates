package hk.edu.uic.mates.model.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	//TDOD 更改SQL语句
	private static final String DB_NAME = "mates.db";
	private static final String DB_CREATE_JOINHISTORY = "CREATE TABLE \"JoinHistory\" (\"id\" INTEGER PRIMARY KEY autoincrement,\"roomId\" INTEGER NOT NULL ,\"times\" INTEGER NOT NULL  DEFAULT (0) ,\"lastJoinTime\" DATETIME NOT NULL  DEFAULT (CURRENT_TIMESTAMP) ,\"userId\" INTEGER NOT NULL  DEFAULT (0) );";
	private static final String DB_CREATE_ROOM = "CREATE TABLE \"Room\" (\"id\" INTEGER PRIMARY KEY autoincrement,\"roomArea\" VARCHAR NOT NULL ,\"roomName\" VARCHAR NOT NULL ,\"openfireRoomId\" VARCHAR NOT NULL ,\"lon\" DOUBLE NOT NULL ,\"lat\" DOUBLE NOT NULL ,\"lonError\" DOUBLE NOT NULL ,\"latError\" DOUBLE NOT NULL );";
	private static final String DB_CREATE_USER = "CREATE TABLE \"User\" (\"id\" INTEGER PRIMARY KEY autoincrement,\"matesId\" INTEGER,\"weiboId\" VARCHAR,\"weiboName\" VARCHAR, \"avatarPathBig\" VARCHAR, \"avatarPathSmall\" VARCHAR);";
																																																																	
	private static final String DB_DROP_JOINHISTORY = "DROP TABLE IF EXISTS \"JoinHistory\"";
	private static final String DB_DROP_ROOM = "DROP TABLE IF EXISTS \"Room\"";
	private static final String DB_DROP_USER = "DROP TABLE IF EXISTS \"User\"";
	
	private static final int DB_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE_JOINHISTORY);
		db.execSQL(DB_CREATE_ROOM);
		db.execSQL(DB_CREATE_USER);
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (1,'A','101','a101@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (2,'A','102','a102@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (3,'B','101','b101@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (4,'B','102','b102@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (5,'B','202','b202@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (6,'B','203','b203@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (7,'C','210','c210@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (8,'C','209','c209@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (9,'C','208','c208@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (10,'C','310','c310@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (11,'D','401','d401@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (12,'D','402','d402@uic.mates',1,1,1,1)");
		db.execSQL("INSERT INTO \"Room\" (\"id\",\"roomArea\",\"roomName\",\"openfireRoomId\",\"lon\",\"lat\",\"lonError\",\"latError\") VALUES (13,'E','302','e302@uic.mates',1,1,1,1)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DB_DROP_JOINHISTORY);
		db.execSQL(DB_DROP_ROOM);
		db.execSQL(DB_DROP_USER);
		onCreate(db);
	}

	public SQLiteDatabase open() throws SQLException {
		return this.getWritableDatabase();
	}

	public void close() {
		this.close();
	}

}
