package com.sunset.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.sunset.bean.Config;
import com.sunset.bean.FeeRule;
import com.sunset.bean.Portal;
import com.sunset.bean.Record;
import com.sunset.bean.UpdateInfo;
import com.sunset.bean.User;
import com.sunset.common.Constant;
import com.sunset.util.SqlliteHander;
public class API {
	
	private final static String API_URL=Constant.SERVER_URL+"/api/";
    
    
	 
	public static User login(String account) throws ClientProtocolException, IOException, JSONException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("account", account);
    	String json = httpPost(API_URL+"cashier_queryByAccount.php",map);
    	/*JSONObject jsonObj = new JSONObject(json);
    	String  key = jsonObj.getString("key");
    	if(key.equals("0"))
    	{
    		jsonObj = jsonObj.getJSONObject("user");
    		return jsonObj.getString("userId");
    	}else 
    	{
    		return key;
    	}*/
    	return  new Gson().fromJson(json, User.class);
    	 
	}
	
	public static Record queryRecord(String carCode,String parkId) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("record.carId", carCode.toUpperCase());
    	map.put("record.parkId", parkId);
    	String json = httpPost(API_URL+"record_query.php",map);
    	/*JSONObject jsonObj = new JSONObject(json);
    	String  key = jsonObj.getString("key");
    	if(key.equals("0"))
    	{
    		jsonObj = jsonObj.getJSONObject("user");
    		return jsonObj.getString("userId");
    	}else 
    	{
    		return key;
    	}*/
    	return  new Gson().fromJson(json, Record.class);
	}
	
	public static ArrayList<LinkedHashMap<String,String>> queryRecordList(String parkId,String status) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("record.status", status);
    	map.put("record.parkId", parkId);
    	String json = httpPost(API_URL+"record_recordList.php",map);
    	/*JSONObject jsonObj = new JSONObject(json);
    	String  key = jsonObj.getString("key");
    	if(key.equals("0"))
    	{
    		jsonObj = jsonObj.getJSONObject("user");
    		return jsonObj.getString("userId");
    	}else 
    	{
    		return key;
    	}*/
    	return  new Gson().fromJson(json, ArrayList.class);
	}
	
	public static FeeRule queryFeeRule(String parkId) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("rule.parkId", parkId);
    	String json = httpPost(API_URL+"feeRule_query.php",map);
    	/*JSONObject jsonObj = new JSONObject(json);
    	String  key = jsonObj.getString("key");
    	if(key.equals("0"))
    	{
    		jsonObj = jsonObj.getJSONObject("user");
    		return jsonObj.getString("userId");
    	}else 
    	{
    		return key;
    	}*/
    	return  new Gson().fromJson(json, FeeRule.class);
	}
	public static String checkVip(String parkId, String carCode) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("parkId", parkId);
    	map.put("carCode", carCode.toUpperCase());
    	String json = httpPost(API_URL+"vip_checkVip.php",map);
    	 
		return json;
	}
	public static String entryRecord(Record record, Context context) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("record.userId", record.getUserId());
    	map.put("record.entry", record.entry);
    	map.put("record.entryImg", record.entryImg);
    	map.put("record.carId", record.carId.toUpperCase());
    	map.put("record.parkId", record.parkId);
    	map.put("record.recordId", record.recordId);
     	String json ;
    	Config config = SqlliteHander.getTnstantiation(context).queryConfig("syncImg");
    	if(config==null || config.value.equals("1"))
    	{
    		 json = httpPost(API_URL+"record_add.php",map,new File(Constant.IMAGE_DIR+"/"+record.entryImg));
    	}else
    	{
    	     json = httpPost(API_URL+"record_add.php",map);
    	}
    	
		return json;
	}
	public static String updateRecord(Record record, Context context) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("record.recordId", record.recordId);
    	map.put("record.export", record.export);
    	map.put("record.exportImg", record.exportImg);
    	if(record.description!=null)
    	map.put("record.description", record.description);
    	map.put("amt", record.shouldAmt);
    	
    	String json ;
    	Config config = SqlliteHander.getTnstantiation(context).queryConfig("syncImg");
    	if(config==null || config.value.equals("1"))
    	{
    		 json = httpPost(API_URL+"record_update.php",map,new File(Constant.IMAGE_DIR+"/"+record.exportImg));
    	}else
    	{
    	     json = httpPost(API_URL+"record_update.php",map);
    	}
    	
		return json;
	}
	public static String syncRecord(Record record, Context context) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("record.userId", record.getUserId());
    	map.put("record.entry", record.entry);
    	map.put("record.entryImg", record.entryImg);
    	map.put("record.carId", record.carId);
    	map.put("record.parkId", record.parkId);
    	map.put("record.recordId", record.recordId);
    	map.put("record.export", record.export);
    	map.put("record.exportImg", record.exportImg);
    	map.put("record.entryTime", record.entryTime);
    	map.put("record.exportTime", record.exportTime);
    	map.put("amt", record.shouldAmt);
    	String json ;
    	Config config = SqlliteHander.getTnstantiation(context).queryConfig("syncImg");
    	if(config==null || config.value.equals("1"))
    	{
    		 json = httpPost(API_URL+"record_sync.php",map,new File(Constant.IMAGE_DIR+"/"+record.entryImg),new File(Constant.IMAGE_DIR+"/"+record.exportImg));
    	}else
    	{
    	     json = httpPost(API_URL+"record_sync.php",map);
    	}
    	
		return json;
	}
	public static ArrayList<Portal> portalList(String parkId) throws ClientProtocolException, IOException, NumberFormatException, JSONException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("parkId", parkId);
    	String json = httpPost(API_URL+"portal_list.php",map);
    	JSONArray array = new JSONArray(json);
    	ArrayList<Portal> list = new ArrayList<Portal>();
		for(int i=0;i<array.length();i++)
		{
			JSONObject obj  = array.getJSONObject(i);
			Portal p = new Portal();
			p.portalId=obj.getString("portalId");
			p.name=obj.getString("name");
			p.parkId = obj.getString("parkId");
			list.add(p);
		}
    	return  list;
	 
	}
	
	public static String modifyPassword(String account, String oldPwd,
			String newPwd) throws ClientProtocolException, IOException {
		Map<String,String> map = new HashMap<String,String>();
    	map.put("cashier.account", account);
    	map.put("newPassword", newPwd);
    	map.put("oldPassword", oldPwd);
    	String json = httpPost(API_URL+"cashier_modifyPassword.php",map);
		return json;
	}
    

	
	 
	
	public static UpdateInfo updateInfo() throws IOException, JSONException {
		
		UpdateInfo updateInfo = new UpdateInfo();
		Map<String,String> map = new HashMap<String,String>();
    	map.put("config.domain", "mimi");
    	String json = httpPost(API_URL+"config_queryConfig.php",map);
    	JSONObject jsonObj = new JSONObject(json);
    	String  key = jsonObj.getString("key");
    	if(!key.equals("0"))
    	{
    		throw new IOException();
    	}
		JSONArray array = jsonObj.getJSONArray("dataList");
		for(int i=0;i<array.length();i++)
		{
			JSONObject obj  = array.getJSONObject(i);
			if(obj.getString("key").equals("app_level"))
			{
				updateInfo.setNewlevel(Integer.parseInt(obj.getString("value")));
			}
			if(obj.getString("key").equals("app_url"))
			{
				updateInfo.setAppUrl(obj.getString("value"));
			}
			if(obj.getString("key").equals("app_version"))
			{
				updateInfo.setNewVersion(obj.getString("value"));
			}
			if(obj.getString("key").equals("updateMsg"))
			{
				updateInfo.setUpdateMsg(obj.getString("value"));
			}
		}
	 
		return updateInfo;
	}
	
	public static String httpPost(String url,Map<String,String> map) throws ClientProtocolException, IOException
	{
		 HttpPost httpPost = new HttpPost(url);  
         // 设置字符集  
		/* List<NameValuePair> params = new ArrayList<NameValuePair>();
		 for(String key:map.keySet())
		 {
			 params.add(new BasicNameValuePair(key,map.get(key)));
		 }
         //HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);  
*/		 MultipartEntity entity = new MultipartEntity(); 
		 for(String key:map.keySet())
		 {
			 StringBody stringBody = new StringBody(map.get(key).toString(),
                     Charset.forName("UTF-8"));
			 entity.addPart(key,stringBody);
		 }
         // 设置参数实体  
         httpPost.setEntity(entity);  
         // 获取HttpClient对象  
         HttpClient httpClient = new DefaultHttpClient();  
         //连接超时  
         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);  
         //请求超时  
         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);  
 
         HttpResponse httpResp = httpClient.execute(httpPost);  
         String json = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
         System.out.println(json);
         return json;
	}
	
	public static String httpPost(String url,Map<String,String> map,File... file) throws ClientProtocolException, IOException
	{
		 HttpPost httpPost = new HttpPost(url);  
         // 设置字符集  
		 MultipartEntity mpEntity = new MultipartEntity(); //文件传输
		 if(file.length > 1)
		 {
			 ContentBody f1 = new FileBody(file[0]);
			 ContentBody f2 = new FileBody(file[1]);
			 mpEntity.addPart("entryImg", f1);
			 mpEntity.addPart("exportImg", f2);
		 }else
		 {
			 ContentBody cbFile = new FileBody(file[0]);
			 mpEntity.addPart("image", cbFile);
		 }
		  
		 for(String key:map.keySet())
		 {
			 StringBody stringBody = new StringBody(map.get(key).toString(),
                     Charset.forName("UTF-8"));
			 mpEntity.addPart(key,stringBody);
		 }
		 
         // 设置参数实体  
         httpPost.setEntity(mpEntity);  
         // 获取HttpClient对象  
         HttpClient httpClient = new DefaultHttpClient();  
         //连接超时  
         httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);  
         //请求超时  
         httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);  
 
         HttpResponse httpResp = httpClient.execute(httpPost);  
         String json = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
         System.out.println(json);
         return json;
	}

	

	
}
