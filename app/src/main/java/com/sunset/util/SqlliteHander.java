package com.sunset.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunset.bean.Config;
import com.sunset.bean.FeeRule;
import com.sunset.bean.Portal;
import com.sunset.bean.Record;
import com.sunset.bean.User;
import com.sunset.common.Constant;

public class SqlliteHander {
	static Context context;
	private static SqlliteHander hander;
	private final static String DATABASE = Constant.APP_NAME+".db"; 
	private SqlliteHander()
	{};
	
	public static SqlliteHander getTnstantiation(Context ctx){
		context = ctx;
		if(null==hander)
		{
			return new SqlliteHander();
		}
		return hander;
	}
	
	public  void createTable()
	{
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 String USQL ="create table if not exists T_sunset_USER (cashierId varchar(13) PRIMARY KEY,name varchar(16),account varchar(16),password varchar(16),parkId varchar(13),telephone varchar(11),status varchar(1));";
		 String PSQL ="create table if not exists T_sunset_PORTAL (account varchar(16) PRIMARY KEY,name varchar(16),portalId varchar(13) ,parkId varchar(13));";
		 String CSQL ="create table if not exists T_sunset_CONFIG (key varchar(16) PRIMARY KEY,value varchar(100));";
		 String MSQL ="create table if not exists T_sunset_RECORD (recordId varchar(13) PRIMARY KEY,carId varchar(10),parkId varchar(13),entryTime varchar(20),exportTime varchar(20),entry varchar(20),export varchar(20),entryImg varchar(20),exportImg varchar(20),shouldAmt varchar(10),userId varchar(16),state varchar(1));";
		 String FSQL ="create table if not exists T_sunset_FEERULE (ruleId varchar(13) PRIMARY KEY,parkId varchar(13),maxFee varchar(4),freeTime varchar(4),firstTime varchar(4),firstPrice varchar(4),spaceTime varchar(4),spacePrice varchar(4));";
		 db.execSQL(USQL);  
		 db.execSQL(MSQL);
		 db.execSQL(CSQL);  
		 db.execSQL(PSQL);
		 db.execSQL(FSQL);
		 db.close();
	}
	
	public  void saveUser(User obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("delete from T_sunset_USER", new Object[]{});
		db.execSQL("INSERT INTO T_sunset_USER(cashierId,name,account,password,parkId,telephone,status) VALUES (?,?,?,?,?,?,?)", new String[]{obj.cashierId,obj.name,obj.account,obj.password,obj.parkId,obj.telephone,obj.status});
		db.close();
	}
	public  void saveConfig(Config obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("delete from T_sunset_CONFIG where key = ?", new Object[]{obj.key});
		if(obj.value != null && !obj.value.equals("") )
		{
		  db.execSQL("INSERT INTO T_sunset_CONFIG(key,value) VALUES (?,?)", new String[]{obj.key,obj.value});
		}
		db.close();
	}
	public  void saveFeeRule(FeeRule obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("delete from T_sunset_FEERULE", new Object[]{});
		db.execSQL("INSERT INTO T_sunset_FEERULE(ruleId,parkId,maxFee,freeTime,firstTime,firstPrice,spaceTime,spacePrice) VALUES (?,?,?,?,?,?,?,?)", new String[]{obj.ruleId,obj.parkId,obj.maxFee,obj.freeTime,obj.firstTime,obj.firstPrice,obj.spaceTime,obj.spacePrice});
		db.close();
	}
	public  void savePortal(String account,Portal obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("delete from T_sunset_PORTAL", new Object[]{});
		db.execSQL("INSERT INTO T_sunset_PORTAL(account,name,portalId,parkId) VALUES (?,?,?,?)", new String[]{account,obj.name,obj.portalId,obj.parkId});
		db.close();
	}
	public  void saveRecord(Record obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  
		db.execSQL("INSERT INTO T_sunset_RECORD(recordId,carId,parkId,entryTime,entryImg,entry,export,userId,state) VALUES (?,?,?,?,?,?,?,?,?)", new String[]{obj.recordId,obj.carId,obj.parkId,obj.entryTime,obj.entryImg,obj.entry,obj.export,obj.userId,obj.state});
		db.close();
	}
	 
	public  void saveFullRecord(Record obj)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  
		db.execSQL("INSERT INTO T_sunset_RECORD(recordId,carId,parkId,entryTime,entryImg,entry,exportTime,exportImg,export,shouldAmt,userId,state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", new String[]{obj.recordId,obj.carId,obj.parkId,obj.entryTime,obj.entryImg,obj.entry,obj.exportTime,obj.exportImg,obj.export,obj.userId,obj.state});
		db.close();
	}
	 
	public  void deleteUser()
	{
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 db.execSQL("delete from T_sunset_USER", new Object[]{});
		 db.close();
	}
	public  void deleteAllMatter()
	{
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 db.execSQL("delete from T_sunset_MATTER", new Object[]{});
		 db.close();
	}
	public  void deleteMatter(String matterId)
	{
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 db.execSQL("delete from T_sunset_MATTER where matterId =?", new Object[]{matterId});
		 db.close();
	}
	
	public  User queryUser()
	{
		 User info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_USER", null);  
	        while (c.moveToNext()) {  
	        	info=new User();
	        	info.cashierId = c.getString(c.getColumnIndex("cashierId"));
	        	info.account = c.getString(c.getColumnIndex("account"));
	        	info.name = c.getString(c.getColumnIndex("name"));
	        	info.parkId = c.getString(c.getColumnIndex("parkId"));
	        	info.status = c.getString(c.getColumnIndex("status"));
	        	info.telephone = c.getString(c.getColumnIndex("telephone"));
	        	info.password = c.getString(c.getColumnIndex("password"));
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
	public  Config queryConfig(String key)
	{
		Config info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_CONFIG where key = ?", new String[]{key});  
	        while (c.moveToNext()) {  
	        	info=new Config();
	        	info.key = c.getString(c.getColumnIndex("key"));
	        	info.value = c.getString(c.getColumnIndex("value"));
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
	public  FeeRule queryFeeRule()
	{
		FeeRule info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_FEERULE", null);  
	        while (c.moveToNext()) {  
	        	info=new FeeRule();
	        	info.freeTime = c.getString(c.getColumnIndex("freeTime"));
	        	info.firstTime = c.getString(c.getColumnIndex("firstTime"));
	        	info.firstPrice = c.getString(c.getColumnIndex("firstPrice"));
	        	info.spaceTime = c.getString(c.getColumnIndex("spaceTime"));
	        	info.spacePrice = c.getString(c.getColumnIndex("spacePrice"));
	        	info.maxFee = c.getString(c.getColumnIndex("maxFee"));
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
	
	public  Portal queryPortal()
	{
		Portal info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_PORTAL", null);  
	        while (c.moveToNext()) {  
	        	info=new Portal();
	        	info.name = c.getString(c.getColumnIndex("name"));
	        	info.portalId = c.getString(c.getColumnIndex("portalId"));
	        	info.parkId = c.getString(c.getColumnIndex("parkId"));
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
 

	public Record queryRecord(String carId) {
		Record info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_RECORD where carId = ? and state < ?", new String[]{carId,"2"});  
	        while (c.moveToNext()) {  
	        	info = mappingRecord(c);
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
	public Record queryRecordById(String recordId) {
		Record info = null;
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  	
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_RECORD where recordId = ? ", new String[]{recordId});  
	        while (c.moveToNext()) {  
	        	info = mappingRecord(c);
	        	 
	        	break;
	        }  
	        c.close(); 
	        db.close();
		return info;
	}
	public ArrayList<Record>  queryRecordList(String account) {
		ArrayList<Record> recordList = new ArrayList<Record>();
		recordList.addAll(listOrderByEntryTime(account));
		recordList.addAll(listOrderByexportTime(account));
		return recordList;
	}
	public ArrayList<Record>  listOrderByEntryTime(String account) { 
		 ArrayList<Record> recordList = new ArrayList<Record>();
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  
		 Cursor c;
		  c = db.rawQuery("SELECT * FROM T_sunset_RECORD where userId = ? and state < ? order by entryTime desc", new String[]{account,"2"});  
	        while (c.moveToNext()) {  
	        	recordList.add(mappingRecord(c));
	        }  
	        c.close(); 
	        db.close();
		return recordList;
	}
	public ArrayList<Record>  listOrderByexportTime(String account) {  
		 ArrayList<Record> recordList = new ArrayList<Record>();
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  
		 Cursor c;
		  c = db.rawQuery("SELECT * FROM T_sunset_RECORD where userId = ? and state > ? order by exportTime desc", new String[]{account,"1"});  
	        while (c.moveToNext()) {  
	        	recordList.add(mappingRecord(c));
	        }  
	        c.close(); 
	        db.close();
		return recordList;
	}
	public ArrayList<Record>  queryLocalRecordList(String account) {
		 ArrayList<Record> recordList = new ArrayList<Record>();
		 SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);  
		 Cursor c = db.rawQuery("SELECT * FROM T_sunset_RECORD where userId = ? and state !='9' ", new String[]{account}); 
		 
	        while (c.moveToNext()) {  
	        	recordList.add(mappingRecord(c));
	        }  
	        c.close(); 
	        db.close();
		return recordList;
	}
	public void updateRecord(Record record) {

		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("update  T_sunset_RECORD set exportTime=? ,exportImg=?,shouldAmt=?,export = ?,state=? where recordId=?", new String[]{record.exportTime,record.exportImg,record.shouldAmt,record.export,record.state,record.recordId});
		db.close();
	}
	public void updateRecordStatus(String recordId,String state) {

		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("update  T_sunset_RECORD set state=?   where recordId=?", new String[]{state,recordId});
		db.close();
	}
	public void deleteRecord(String recordId) {
		SQLiteDatabase db = context.openOrCreateDatabase(DATABASE, Context.MODE_PRIVATE, null);
		db.execSQL("delete from T_sunset_RECORD where recordId=?", new Object[]{recordId});		
		db.close();
	}
	
	private Record mappingRecord(Cursor c)
	{
		Record info=new Record();
    	info.carId = c.getString(c.getColumnIndex("carId"));
    	info.entryTime = c.getString(c.getColumnIndex("entryTime"));
    	info.entry = c.getString(c.getColumnIndex("entry"));
    	info.export = c.getString(c.getColumnIndex("export"));
    	info.parkId = c.getString(c.getColumnIndex("parkId"));
    	info.userId = c.getString(c.getColumnIndex("userId"));
    	info.entryImg = c.getString(c.getColumnIndex("entryImg"));
    	info.exportImg = c.getString(c.getColumnIndex("exportImg"));
    	info.exportTime = c.getString(c.getColumnIndex("exportTime"));
    	info.shouldAmt =  c.getString(c.getColumnIndex("shouldAmt"));
    	info.recordId =  c.getString(c.getColumnIndex("recordId"));
    	info.state = c.getString(c.getColumnIndex("state"));
    	return info;
	}
}
