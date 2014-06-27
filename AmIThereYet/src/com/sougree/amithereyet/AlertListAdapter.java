package com.sougree.amithereyet;

import java.util.ArrayList;
import java.util.List;

import com.sougree.amithereyet.dao.AlertDAO;
import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.AlertWithInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlertListAdapter extends BaseAdapter implements ListAdapter {

	private List<AlertWithInfo> alerts = new ArrayList<AlertWithInfo>();
	private Context context;
	
	public AlertListAdapter(Context context, List<AlertWithInfo> alerts) {
		this.alerts.clear();
		this.alerts.addAll(alerts);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return alerts.size();
	}

	@Override
	public Object getItem(int position) {
		return alerts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alerts.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    
	    if (view == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        view = inflater.inflate(R.layout.alert_list_item_view, null);
	    }
	    
	    AlertWithInfo alert = alerts.get(position);

	    TextView listItemText = (TextView)view.findViewById(R.id.listAlertName); 
	    listItemText.setText(alert.getAlertName()); 
	    listItemText.setTag(alert);

	    //Handle buttons and add onClickListeners
	    ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.delete_btn);
	    deleteBtn.setTag(alert);

	    deleteBtn.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v) { 
	        	AlertDAO dao = new AlertDAO(context.getContentResolver());
	        	Alert thisAlert = (Alert)v.getTag();
	        	if(dao.deleteAlert(thisAlert.getId()) > 0) {
	        		Toast.makeText(context, "Deleted! ", Toast.LENGTH_SHORT).show();
	        		alerts.remove(thisAlert);
	        		notifyDataSetChanged();
	        	}
	        }
	    });
	    
	    listItemText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.sougree.amithereyet.EDIT_ALERT");
				Alert thisAlert = (Alert)v.getTag();
				i.putExtra(AlertContentProvider._ID, thisAlert.getId());
				context.startActivity(i);
			}
		});
	    
	    TextView listItemInfo = (TextView)view.findViewById(R.id.listAlertInfo); 
	    listItemInfo.setText(alert.getInfo()); 

	    return view; 
	}

}
