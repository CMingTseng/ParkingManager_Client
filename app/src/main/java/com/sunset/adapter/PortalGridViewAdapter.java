package com.sunset.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunset.bean.Portal;
import com.sunset.ui.R;

public class PortalGridViewAdapter extends BaseAdapter {
       private Context context;
       
       List<Portal> data;
	   public PortalGridViewAdapter(Context context,List<Portal> d)
       {
    	   this.context = context;
    	   data = d;
       }

        public View getView(int index, View arg1, ViewGroup arg2) {
 		        View itemView = LayoutInflater.from(context).inflate(R.layout.item_portal, null);
 		        TextView label = (TextView) itemView.findViewById(R.id.label);
 		        TextView portalId = (TextView) itemView.findViewById(R.id.portalId);
 		        TextView parkId = (TextView) itemView.findViewById(R.id.parkId);
 		        label.setText(data.get(index).name);
 		        parkId.setText(data.get(index).parkId);
 		        portalId.setText(data.get(index).portalId);
                return itemView;
        }

		@Override
		public int getCount() {
			if(data == null)
			{
				return 0;
			}
			return data.size();
		}
		 public Object getItem(int arg0) {
             // TODO Auto-generated method stub
             return null;
     }

     public long getItemId(int arg0) {
             // TODO Auto-generated method stub
             return 0;
     }

     
     
     
	 

}