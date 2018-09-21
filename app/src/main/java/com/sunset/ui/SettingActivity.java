package com.sunset.ui;

import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.sunset.bean.Config;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;

public class SettingActivity extends Activity implements OnClickListener{
	Config pconfig;
	ConnectivityManager mConnectivityManager;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		pconfig = SqlliteHander.getTnstantiation(this).queryConfig("prefix");
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText(R.string.system_setting);
	    (findViewById(R.id.button_return)).setOnClickListener(this);
	    (findViewById(R.id.SaveButton)).setOnClickListener(this);
	    findViewById(R.id.about_item).setOnClickListener(this);
	    findViewById(R.id.syncImgSwitch).setOnClickListener(this);
	    findViewById(R.id.netSwitch).setOnClickListener(this);
	    findViewById(R.id.wifiSwitch).setOnClickListener(this);
	    findViewById(R.id.blueSwitch).setOnClickListener(this);
	    findViewById(R.id.SaveDayButton).setOnClickListener(this);
	    findViewById(R.id.password_item).setOnClickListener(this);
	    ((EditText)findViewById(R.id.prefix)).setText(pconfig==null?"":pconfig.value);
	    
	    init();
	}

	private void init() {
		WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {  
			    ((CheckBox)findViewById(R.id.wifiSwitch)).setChecked(true);
		} else
		{  
				((CheckBox)findViewById(R.id.wifiSwitch)).setChecked(false);
		}
		
		
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled()) {  
		    ((CheckBox)findViewById(R.id.blueSwitch)).setChecked(true);
		} else {  
			((CheckBox)findViewById(R.id.blueSwitch)).setChecked(false);
		}
		
		
	   Config config = SqlliteHander.getTnstantiation(this).queryConfig("syncImg");	
	   if(config==null || config.value.equals("1")){
		   ((CheckBox)findViewById(R.id.syncImgSwitch)).setChecked(true);
	   }else
	   {
		   ((CheckBox)findViewById(R.id.syncImgSwitch)).setChecked(false);
	   }
   
	   Config deadline = SqlliteHander.getTnstantiation(this).queryConfig("deadline");	
	   if(deadline!=null ){
		   ((EditText)findViewById(R.id.day)).setText(deadline.value);
	   } 
	   
	   mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	   boolean linked=mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
	   if(linked)
	   {
		   ((CheckBox)findViewById(R.id.netSwitch)).setChecked(true);
	   }else
	   {
		   ((CheckBox)findViewById(R.id.netSwitch)).setChecked(false);
	   }
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		   case R.id.about_item:
			    Intent intent = new Intent(this,AboutActivity.class);  
				startActivity(intent);
			   break;
		   case R.id.password_item:
			    Intent pintent = new Intent(this,PasswordActivity.class);  
				startActivity(pintent);
			   break;
		   case R.id.SaveDayButton:
			   String day = ((EditText)findViewById(R.id.day)).getText().toString().trim();
			   /*if(day==null || day.equals(""))
			   {
				   BaseContorl.doShowEToask(this, "请输入保存的天数!");
				   return;
			   }*/
			   if(!day.equals("")&&Integer.parseInt(day)<=0)
			   {
				   BaseContorl.doShowEToask(this, "请输入大于0的天数!");
				   return;
			   }
			   Config d = new Config();
			   d.key="deadline";
			   d.value = day;
			   SqlliteHander.getTnstantiation(this).saveConfig(d);
			   BaseContorl.doShowSToask(this, "保存成功!");
			   break;
		   case R.id.SaveButton:
			   String prefix = ((EditText)findViewById(R.id.prefix)).getText().toString().trim();
			  /* if(!prefix.equals(""))
			   {
				   
			   }else
			   {
				   BaseContorl.doShowEToask(this, "车牌前缀不能为空!");
			   }*/
			   Config pconfig = new Config();
			   pconfig.key = "prefix";
			   pconfig.value = prefix;
			   SqlliteHander.getTnstantiation(this).saveConfig(pconfig);
			   BaseContorl.doShowSToask(this, "保存成功!"); 
			   break;
		   case R.id.button_return:
			   SettingActivity.this.finish();
			   break; 
		   case R.id.syncImgSwitch:
			   boolean i = ((CheckBox)findViewById(R.id.syncImgSwitch)).isChecked();
			   Config config = new Config();
			   config.key="syncImg";
			   if(!i)
               {
				   config.value="0";
               }else
               {
            	   config.value="1";
               }
			   SqlliteHander.getTnstantiation(this).saveConfig(config);
			   break; 
		   case R.id.netSwitch:
			   boolean c = ((CheckBox)findViewById(R.id.netSwitch)).isChecked();
			   if(!c)
               {   
				   setMobileNetUnable();
				   BaseContorl.doShowHToask(this, "正在关闭移动网络连接......");
               }else
               {
            	   setMobileNetEnable();
            	   BaseContorl.doShowHToask(this, "正在打开移动网络连接......");
               }
			   break; 
		   case R.id.wifiSwitch:
			   WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
               boolean s = ((CheckBox)findViewById(R.id.wifiSwitch)).isChecked();
               if(!s)
               {
            	   ((CheckBox)findViewById(R.id.wifiSwitch)).setChecked(false);
            	   BaseContorl.doShowHToask(this, "正在关闭WIFI......");
            	   wifiManager.setWifiEnabled(false);
               }else
               {
            	   ((CheckBox)findViewById(R.id.wifiSwitch)).setChecked(true);
            	   BaseContorl.doShowHToask(this, "正在打开WIFI......");
            	   wifiManager.setWifiEnabled(true);
               }
			   break;
		   case R.id.blueSwitch:
			   BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			   boolean b = ((CheckBox)findViewById(R.id.blueSwitch)).isChecked();
			   if(!b)
               {
            	   ((CheckBox)findViewById(R.id.blueSwitch)).setChecked(false);
            	   BaseContorl.doShowHToask(this, "正在关闭蓝牙......");
            	   adapter.disable();
               }else
               {
            	   ((CheckBox)findViewById(R.id.blueSwitch)).setChecked(true);
            	   BaseContorl.doShowHToask(this, "正在打开蓝牙......");
            	   adapter.enable();
               }
			   break;
		}
		
	}
	
	
	
	
	
	public final void setMobileNetEnable() {
		
		mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		Object[] arg = null;
		try {
			boolean isMobileDataEnable = invokeMethod("getMobileDataEnabled",
					arg);
			if (!isMobileDataEnable) {
				invokeBooleanArgMethod("setMobileDataEnabled", true);
				
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	
	
	public final void setMobileNetUnable() {
		 mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		Object[] arg = null;
		try {
			boolean isMobileDataEnable = invokeMethod("getMobileDataEnabled",
					arg);
			if (isMobileDataEnable) {
				invokeBooleanArgMethod("setMobileDataEnabled", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean invokeMethod(String methodName, Object[] arg)
			throws Exception {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		Class ownerClass = mConnectivityManager.getClass();

		Class[] argsClass = null;
		if (arg != null) {
			argsClass = new Class[1];
			argsClass[0] = arg.getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);

		Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);

		return isOpen;
	}

	public Object invokeBooleanArgMethod(String methodName, boolean value)
			throws Exception {

		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		Class ownerClass = mConnectivityManager.getClass();

		Class[] argsClass = new Class[1];
		argsClass[0] = boolean.class;

		Method method = ownerClass.getMethod(methodName, argsClass);

		return method.invoke(mConnectivityManager, value);
	}
  
 
}
