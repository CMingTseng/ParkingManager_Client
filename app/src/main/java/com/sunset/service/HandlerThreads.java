package com.sunset.service;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sunset.bean.Config;
import com.sunset.bean.FeeRule;
import com.sunset.bean.Record;
import com.sunset.bean.UpdateInfo;
import com.sunset.common.Constant;
import com.sunset.ui.HomeActivity;
import com.sunset.ui.NewVersionActivity;
import com.sunset.ui.R;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;

public  class HandlerThreads {

	 
	public static  class RecordAddThread extends Thread{
		
	 
		Handler handler;
		Record record;
		Context context;
		public RecordAddThread(Handler handler,Record r,Context c)
		{
			 
			this.handler = handler;
			this.record = r;
			context = c;
		}
		
		public void run() {
			Message message=new Message();
			String code=null;
			try {
				 code = API.entryRecord(record, context);
				
			} catch (Exception e) {
				message.what = -1;
			}
			 
			Bundle bundle=new Bundle();
			bundle.putInt("code", Integer.parseInt(code==null?"0":code));
			bundle.putSerializable("record", record);
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
	
	 
 
	//---------------------------------------------------------------------------------------------------------------
	
	public static  class RecordUpdateThread extends Thread{
		
		 
		Handler handler;
		Record record;
		Context context;
		public RecordUpdateThread(Handler handler,Record r,Context c)
		{
			 
			this.handler = handler;
			this.record = r;
			context = c;
		}
		
		public void run() {
			Message message=new Message();
			String code=null;
			try {
				 code = API.updateRecord(record, context);
				
			} catch (Exception e) {
				e.printStackTrace();
				message.what = -1;
			}
			 
			Bundle bundle=new Bundle();
			bundle.putInt("code", Integer.parseInt(code==null?"0":code));
			bundle.putSerializable("record", record);
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
	
	
	//---------------------------------------------------------------------------------------------------------------
	public static  class QueryRecordThread extends Thread{
		
		 
		Handler handler;
		String carCode;
		String parkId;
		Context context;
		public QueryRecordThread(Context con,Handler handler,String  c,String parkId)
		{
			 
			this.handler = handler;
			this.carCode = c;
			this.parkId = parkId;
			this.context = con;
		}
		
		public void run() {
			Message message=new Message();
			Record r  = SqlliteHander.getTnstantiation(context).queryRecord(carCode);
			if(r == null){
				try {
					 
					 r = API.queryRecord(carCode,parkId);
					
				} catch (Exception e) {
					e.printStackTrace();
					message.what = -1;
				}
			}
			Bundle bundle=new Bundle();
			bundle.putSerializable("record", r);
			bundle.putString("carId", carCode);
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
//---------------------------------------------------------------------------------------------------------------
	
	public static  class CheckVipThread extends Thread{
		
		 
		Handler handler;
		String carCode;
		String parkId;
		public CheckVipThread(Handler handler,String  parkId,String code)
		{
			 
			this.handler = handler;
			this.carCode = code;
			this.parkId = parkId;
			
		}
		
		public void run() {
			Message message=new Message();
			String r=null;
			try {
				 r = API.checkVip(parkId,carCode);
			} catch (Exception e) {
				e.printStackTrace();
				message.what = -1;
			}
			 
			Bundle bundle=new Bundle();
			bundle.putBoolean("isVip", "1".equals(r));
			message.setData(bundle);
			handler.sendMessage(message);
		}
	}
		
	
	
	//---------------------------------------------------------------------------------------------------------------
	
 
	public static  class CleanOldFileThread extends Thread{
		
		 
		 
		Context context;
		public CleanOldFileThread(Context c)
		{
			 
			context = c;
		}
		
		public void run() {
			 Config config = SqlliteHander.getTnstantiation(context).queryConfig("deadline");
			 if(config!=null)
			 {
				   int deadline = Integer.parseInt(config.value);
				   if(deadline>0)
				   {
					   try{
						    File dir = new File(Constant.IMAGE_DIR);
						    Calendar date = Calendar.getInstance();
					        date.add(Calendar.DATE, -deadline);
					        Long c = date.getTimeInMillis();
						   for(File f : dir.listFiles())
						   {
							   String name = f.getName().split("[.]")[0];
							   if(c > Long.parseLong(name))
							   {
								   f.delete();
							   }
						   }
					   }catch(Exception e)
					   {
						   e.printStackTrace();
					   }
					   
				   }
				 
			 }
		}
	}
	
	
	
	//---------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
	
 
	
	
	//---------------------------------------------------------------------------------------------------------------

	 
	//---------------------------------------------------------------------------------------------------------------

	//---------------------------------------------------------------------------------------------------------------
 
	//---------------------------------------------------------------------------------------------------------------		
		
		

      public  static class CheckUpdateThread extends Thread{
		
	       
	     	Activity activity;
	     	public CheckUpdateThread(Activity a)
	     	{
	     		this.activity = a;
	     	}
			public void run() {
					
				    Message message=new Message();
					UpdateInfo updateInfo = null;
					try {
						updateInfo = API.updateInfo();
					} catch (Exception e) {
						e.printStackTrace();
						message.what = -1;
					} 
					Bundle bundle=new Bundle();
					bundle.putSerializable("updateInfo", updateInfo);
					message.setData(bundle);
					handler.sendMessage(message);
			}
			Handler handler = new Handler() {
					
					public void handleMessage(Message msg) {
						
						final UpdateInfo updateInfo=(UpdateInfo) msg.getData().getSerializable("updateInfo");
						int versionCode = 1;
						try {
						 versionCode = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode;
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
						if(null==updateInfo || updateInfo.getNewlevel()<=versionCode)
						{
							BaseContorl.doShowSToask(activity, R.string.no_new_verson_tips);
						}
						else
						{
							Intent intent = new Intent(activity,NewVersionActivity.class);   
					        intent.putExtra("updateInfo", updateInfo); 
					        activity.startActivity(intent);
						} 
					}
				};
				
		 }
		
	  
  	
  	//---------------------------------------------------------------------------------------------------------------

  		
  		
      public  static class QueryFeeRuleThread extends Thread{
  		
    	    Activity activity;
	     	public QueryFeeRuleThread(Activity a)
	     	{
	     		this.activity = a;
	     	}
			public void run() {
					
				try {
					FeeRule rule = API.queryFeeRule(SqlliteHander.getTnstantiation(activity).queryUser().parkId);
					if(rule!=null)
					{
						SqlliteHander.getTnstantiation(activity).saveFeeRule(rule);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		 }	
		
      
  	 
      
}
