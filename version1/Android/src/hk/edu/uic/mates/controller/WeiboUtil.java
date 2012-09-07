package hk.edu.uic.mates.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public class WeiboUtil {

	private static final String CONSUMER_KEY = "2533962655";
	private static final String CONSUMER_SECRET = "6bc80e55c9ddc25a4a501a71f5c4dc07";
	private static final String REDIRECT_URL = "http://2090.me";
	private Activity currentActivity;
	private Class<?> nextActivity;

	private Weibo weibo;

	public WeiboUtil() {
		
	}
	
	/**
	 * 构造方法
	 * 获得当前activity
	 * 供通过验证后发布消息时用
	 * 
	 * @param currentActivity
	 * @param nextActivity
	 */
	public WeiboUtil(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	
	/**
	 * 构造方法
	 * 获得当前activity及验证通过后转向的activity
	 * 供验证时用
	 * 
	 * @param currentActivity
	 * @param nextActivity
	 */
	public WeiboUtil(Activity currentActivity, Class<?> nextActivity) {
		
		this.currentActivity = currentActivity;
		this.nextActivity = nextActivity;
	}
	
	/**
	 * OAuth2.0 验证
	 * 
	 * @param activity
	 * @param listener
	 * @return TODO 要返回什么呢？
	 */
	public Weibo auth(Activity activity) {
	//	Log.d(Config.TAG, "Weibo auth");
		this.weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);
		weibo.setRedirectUrl(REDIRECT_URL);
		weibo.authorize(currentActivity, new AuthDialogListener());
		return null;
	}

	public boolean isAuthed() {
		this.weibo = Weibo.getInstance();
		if(weibo.getAccessToken() != null) {
			return true;
		} 
		return false;
	}
	
	public String getUserInfo(String uid) {
		this.weibo = Weibo.getInstance();
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("uid", uid);
		String url = Weibo.SERVER + "users/show.json";
		String result;
		try {
			result = weibo.request(currentActivity.getApplicationContext(), url, bundle, Utility.HTTPMETHOD_GET/*, weibo.getAccessToken()*/);
	//		Log.d("Mates", result);
			return result;
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean postMsg(String message) {
		this.weibo = Weibo.getInstance();
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("status", message);
		String url = Weibo.SERVER + "statuses/update.json";
		
		try {
			weibo.request(currentActivity.getApplicationContext(), url, bundle, Utility.HTTPMETHOD_POST);
			return true;
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean follow(String uid) {
		this.weibo = Weibo.getInstance();
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("uid", uid);
		String url = Weibo.SERVER + "friendships/create.json";
		
		try {
			//weibo.request(currentActivity.getApplicationContext(), url, bundle, Utility.HTTPMETHOD_POST);
			
			weibo.request(currentActivity.getApplicationContext(), url, bundle, Utility.HTTPMETHOD_POST);
			return true;
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	class AuthDialogListener implements WeiboDialogListener {
 
		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			String uid = values.getString("uid");
			AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET);
			accessToken.setExpiresIn(expires_in);
			Weibo.getInstance().setAccessToken(accessToken);
	//		Log.d(Config.TAG, "AuthDialogListener onComplete");
			Intent intent = new Intent();
			intent.putExtra("weiboId", uid);
			intent.setClass(currentActivity, nextActivity);
			currentActivity.startActivity(intent);
			currentActivity.finish();
		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(currentActivity.getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			/*Toast.makeText(currentActivity.getApplicationContext(),
					"Auth cancel", Toast.LENGTH_LONG).show();*/
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(currentActivity.getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}
	
}
