package com.sougree.amithereyet;

import java.util.ArrayList;
import java.util.List;

import com.sougree.amithereyet.model.Alert;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AlertListAdapter extends BaseAdapter implements ListAdapter {

	private List<Alert> alertNameList = new ArrayList<Alert>();
	private Context context;
	
	public AlertListAdapter(Context context, Cursor cursor) {
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Alert alert = new Alert();
				alert.setAlertName(cursor.getString(cursor.getColumnIndex(AlertContentProvider.NAME)));
				alert.setLatitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LATITUDE)));
				alert.setLongitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LONGITUDE)));
				alert.setRadius(cursor.getInt(cursor.getColumnIndex(AlertContentProvider.RADIUS)));
				
				alertNameList.add(alert);
			} while(cursor.moveToNext());
		}
		
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return alertNameList.size();
	}

	@Override
	public Object getItem(int position) {
		return alertNameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        view = inflater.inflate(R.layout.alert_list_item_view, null);
	    } 

	    TextView listItemText = (TextView)view.findViewById(R.id.listAlertName); 
	    listItemText.setText(alertNameList.get(position).getAlertName()); 

	    //Handle buttons and add onClickListeners
	    Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
	    deleteBtn.setTag(Integer.valueOf(position));

	    deleteBtn.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v) { 
	            //do something
	        	//alertNameList.remove(position); //or some other task
	            //notifyDataSetChanged();
	        	
	        	Toast.makeText(context, "Delete " + alertNameList.get((Integer)v.getTag()), Toast.LENGTH_SHORT).show();;
	        }
	    });
	    
	    TextView listItemInfo = (TextView)view.findViewById(R.id.listAlertInfo); 
	    listItemInfo.setText(alertNameList.get(position).toString()); 

	    return view; 
	}

}
