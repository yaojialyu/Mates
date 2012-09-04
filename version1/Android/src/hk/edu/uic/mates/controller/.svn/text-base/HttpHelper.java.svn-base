package hk.edu.uic.mates.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpHelper {
	private String url;
	private HttpPost httpRequest;
	private HttpClient httpClient;
	private List<NameValuePair> params;
	private HttpEntity httpEntity;
	private String result;
	
	public HttpHelper() {
		
	}
	
	public HttpHelper(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
	}
	
	public void initConnection() {
		httpRequest = new HttpPost(url);
		httpClient = new DefaultHttpClient();
	}
	
	public void addParams(String key, String value) {
		params.add(new BasicNameValuePair(key, value));
	}
	
	public String start() {
		result = this.sendRequest();
		return result;
	}
	
	private String sendRequest() {
		
		try {
			httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httpRequest.setEntity(httpEntity);
			
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return this.receiveResponse(httpResponse);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String receiveResponse(HttpResponse httpResponse) {
		StringBuilder tmpResult = new StringBuilder();
		
		HttpEntity responseEntity = httpResponse.getEntity();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(responseEntity.getContent()));
			
			String line = null;
			if ((line = reader.readLine()) != null) {
				if (!line.equals("false")) {
					//System.out.println(line);
					tmpResult.append(line + "\n");
				} else {
					return null;
				}
			}
			reader.close();
			return tmpResult.toString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
