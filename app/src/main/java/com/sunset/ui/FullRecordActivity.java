package com.sunset.ui;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sunset.adapter.FullRecordListViewAdapter;
import com.sunset.bean.Record;
import com.sunset.service.API;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;
public class FullRecordActivity extends Activity{
	
	Button refreshButton;
	ProgressDialog progressDialog;
	ArrayList<LinkedHashMap<String,String>> recordList;
	ListView  listtView;
	String parkId;
	FullRecordListViewAdapter listAdapter;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_record);
		listtView = (ListView) findViewById(R.id.recordListtView);
		refreshButton = (Button) findViewById(R.id.RIGHT_BUTTON);
		refreshButton.setVisibility(View.VISIBLE);
		refreshButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				progressDialog = ProgressDialog.show(FullRecordActivity.this, "提示", "正在查询,请稍后...", true, false);
				new QueryThread(queryHandler).start();
			}
			
		});
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("今日收费记录");
	   ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				FullRecordActivity.this.finish();
			}});
	   
	   
	    listtView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				Record record = new Record();
				record.setEntryImg(listAdapter.getItem(index).get("entryImg"));
				record.setExportImg(listAdapter.getItem(index).get("exportImg"));
				Intent intent = new Intent(FullRecordActivity.this,PreviewActivity.class);   
                intent.putExtra("record", record);
                intent.putExtra("net", true);
        		startActivity(intent);
		}});//
	   
		parkId = SqlliteHander.getTnstantiation(FullRecordActivity.this).queryPortal().parkId;
		listAdapter = new FullRecordListViewAdapter(this,recordList);
		listtView.setAdapter(listAdapter);
		progressDialog = ProgressDialog.show(this, "提示", "正在查询,请稍后...", true, false);
		new QueryThread(queryHandler).start();
	}
	
	
	Handler queryHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if(recordList == null || recordList.isEmpty())
			{
				BaseContorl.doShowHToask(FullRecordActivity.this, "今日无记录!");
     		   return;
			}
			listAdapter.setDataList(recordList);
			listAdapter.notifyDataSetChanged();
			((TextView)findViewById(R.id.icon_count)).setText(String.valueOf(String.valueOf(recordList.size())));
			((TextView)findViewById(R.id.icon_count)).setVisibility(View.VISIBLE);
		}
	};
	
	public   class QueryThread extends Thread{
  		
        Handler handler;
     	public QueryThread(Handler h)
     	{
     		handler = h;
     	}
		public void run() {
				
				try {
					recordList = (ArrayList<LinkedHashMap<String,String>>) API.queryRecordList(parkId, "1");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                     
				handler.sendEmptyMessage(0);
		}
	 }	
	
}
