package com.sunset.adapter;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunset.ui.R;
import com.sunset.util.AppTools;

public class ActivieRecordListViewAdapter extends BaseAdapter {
       private Context context;
       ArrayList<LinkedHashMap<String,String>> dataList;
       boolean frist = true;
	   public ActivieRecordListViewAdapter(Context context, ArrayList<LinkedHashMap<String,String>> data)
       {
    	   this.context = context;
    	   this.dataList = data;
       }

        public View getView(int index, View itemView, ViewGroup arg2) {
        	
        	     if(itemView==null)
        	     {
        	    	 itemView = LayoutInflater.from(context).inflate(R.layout.item_active_record, null);
        	     }
 		        ((TextView) itemView.findViewById(R.id.carId)).setText(dataList.get(index).get("carId").toString());;
 		        ((TextView) itemView.findViewById(R.id.entryTime)).setText(AppTools.dateToDate(dataList.get(index).get("entryTime").toString()));
 		        return itemView;
        }

		@Override
		public int getCount() {
			if(dataList == null)
			{
				return 0;
			}
			return dataList.size();
		}
		 public LinkedHashMap<String,String> getItem(int index) {
             // TODO Auto-generated method stub
             return dataList.get(index);
     }

     public long getItemId(int arg0) {
             // TODO Auto-generated method stub
             return 0;
     }

	public ArrayList<LinkedHashMap<String,String>> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<LinkedHashMap<String,String>> dataList) {
		this.dataList = dataList;
	}
	
 
	 
}