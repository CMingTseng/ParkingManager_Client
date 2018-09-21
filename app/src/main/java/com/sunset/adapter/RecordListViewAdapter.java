package com.sunset.adapter;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunset.bean.Record;
import com.sunset.ui.R;
import com.sunset.util.AppTools;

public class RecordListViewAdapter extends BaseAdapter {
       private Context context;
       ArrayList<Record> dataList;
       boolean frist = true;
	   public RecordListViewAdapter(Context context, ArrayList<Record> data)
       {
    	   this.context = context;
    	   this.dataList = data;
       }

        public View getView(int index, View itemView, ViewGroup arg2) {
        	
        	    if(itemView==null)
        	    {
        	    	 itemView = LayoutInflater.from(context).inflate(R.layout.item_record, null); 
        	    }
 		        ((TextView) itemView.findViewById(R.id.carId)).setText(dataList.get(index).carId);;
 		        ((TextView) itemView.findViewById(R.id.entryTime)).setText(AppTools.timeToTime(dataList.get(index).entryTime));
 		        if(dataList.get(index).exportTime!=null)
 		        {
 	 		        ((TextView) itemView.findViewById(R.id.exportTime)).setText(AppTools.timeToTime(dataList.get(index).exportTime));
 		        }
 		        ((TextView) itemView.findViewById(R.id.amt)).setText(dataList.get(index).shouldAmt);
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
		 public Record getItem(int index) {
             // TODO Auto-generated method stub
             return dataList.get(index);
     }

     public long getItemId(int arg0) {
             // TODO Auto-generated method stub
             return 0;
     }

	public ArrayList<Record> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<Record> dataList) {
		this.dataList = dataList;
	}
	
 
	 
}