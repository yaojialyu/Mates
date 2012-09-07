package hk.edu.uic.mates.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtil {

	public static final File SD_DIR = Environment.getExternalStorageDirectory();
	public static final String AVATAR_PATH_BIG = SD_DIR + "/mates/avatar/big/";
	public static final String AVATAR_PATH_SMALL = SD_DIR + "/mates/avatar/small/";
	public static final int BIG = 1;
	public static final int SMALL = 2;
	public File dir_big;
	public File dir_small;
	
	public FileUtil() {
		super();
		dir_big = new File(AVATAR_PATH_BIG);
		if(!dir_big.exists()) {
			dir_big.mkdirs();
		}
		dir_small = new File(AVATAR_PATH_SMALL);
		if(!dir_small.exists()) {
			dir_small.mkdirs();
		}
	}
	/**
	 * 保存用户头像至sd卡，作为缓存
	 * 返回存储路径
	 * 
	 * @param avatar 用户头像
	 * @param weiboId 用户weiboId
	 * @param size 尺寸(1为大，2为小)
	 * @return path 头像存储路径
	 */
	public String writeAvatar(String weiboId ,Bitmap avatar, int size) {
		File fileToSave;
		if(size == BIG) {
			fileToSave = new File(dir_big + "/" + weiboId + ".jpg");
			
		} else {
			fileToSave = new File(dir_small + "/" + weiboId + ".jpg");
		}
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileToSave));
			avatar.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
			return fileToSave + "";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过path获取用户头像
	 * 
	 * @param path 用户头像存储路径
	 * @return avatar 用户头像
	 */
	public Bitmap readAvatar(String path) {
		Bitmap bm;
		bm = BitmapFactory.decodeFile(path);
		return bm;
	}
	
}
