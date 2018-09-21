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

public class FullRecordListViewAdapter extends BaseAdapter {
       private Context context;
       ArrayList<LinkedHashMap<String,String>> dataList;
       boolean frist = true;
	   public FullRecordListViewAdapter(Context context, ArrayList<LinkedHashMap<String,String>> data)
       {
    	   this.context = context;
    	   this.dataList = data;
       }

        public View getView(int index, View itemView, ViewGroup arg2) {
        	
        	    ViewHolder holder;
        	    if(itemView==null){
 		             itemView = LayoutInflater.from(context).inflate(R.layout.item_full_record, null);
	 		         holder = new ViewHolder();
	                 holder.carId = ((TextView) itemView.findViewById(R.id.carId));
	                 holder.amt = ((TextView) itemView.findViewById(R.id.amt));
	                 holder.entryTime = ((TextView) itemView.findViewById(R.id.entryTime));
	                 holder.exportTime = ((TextView) itemView.findViewById(R.id.exportTime));
	                 itemView.setTag(holder);
        	    }else{
                    holder = (ViewHolder)itemView.getTag();
                }
        	    holder.carId.setText(dataList.get(index).get("carId").toString());;
        	    holder.entryTime.setText(AppTools.timeToTime(dataList.get(index).get("entryTime").toString()));
 		        holder.amt.setText(dataList.get(index).get("shouldAmt").toString());
 		        holder.exportTime.setText(AppTools.timeToTime(dataList.get(index).get("exportTime").toString()));
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
	
	static class ViewHolder {
        TextView carId;
        TextView entryTime;
        TextView amt;
        TextView exportTime;
    }
	 
}