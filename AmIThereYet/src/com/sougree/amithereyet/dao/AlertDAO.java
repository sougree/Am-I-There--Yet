package com.sougree.amithereyet.dao;

import java.util.ArrayList;
import java.util.List;

import com.sougree.amithereyet.AlertContentProvider;
import com.sougree.amithereyet.R;
import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.Notification;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Switch;

/**
 * DAO to CURD alert
 * @author Soundra Pandian
 */
public class AlertDAO {
	
	private ContentResolver cr;
	
	public AlertDAO(ContentResolver cr)	{
		this.cr = cr;
	}
	
	/**
	 * Add Alert
	 * @param nameTxt
	 * @param latTxt
	 * @param lonTxt
	 * @param radTxt
	 * @param checkboxes
	 */
	public Uri addAlert(EditText nameTxt, EditText latTxt, EditText lonTxt, EditText radTxt, Switch... switches) {
		Alert alert = new Alert();
		alert.setAlertName(nameTxt.getText().toString());
		alert.setLatitude(Float.parseFloat(latTxt.getText().toString()));
		alert.setLongitude(Float.parseFloat(lonTxt.getText().toString()));
		alert.setRadius(Integer.parseInt(radTxt.getText().toString()));
		
		for (Switch chk: switches) {
			if(chk.isChecked()) {
				switch(chk.getId()) {
					case R.id.toastChk:
						alert.addNotification(Notification.TOAST);
					break;
					case R.id.ringChk:
						alert.addNotification(Notification.RING);
					break;
				}
			}
		}
		
		ContentValues values = new ContentValues();
		values.put(AlertContentProvider.NAME, alert.getAlertName());
		values.put(AlertContentProvider.LATITUDE, alert.getLatitude());
		values.put(AlertContentProvider.LONGITUDE, alert.getLongitude());
		values.put(AlertContentProvider.RADIUS, alert.getRadius());
		values.put(AlertContentProvider.NOTIFICATIONS, alert.getNotifications().toString());
		
		Uri uri = cr.insert(AlertContentProvider.CONTENT_URI, values);
		
		return uri;
	}
	
	/**
	 * This method deletes a given alert
	 * @param cr
	 * @param dbPK
	 * @return
	 */
	public int deleteAlert(int dbPK) {
		Uri uri = Uri.withAppendedPath(AlertContentProvider.CONTENT_URI, dbPK+"");
		return cr.delete(uri, null, null);
	}
	
	public List<Alert> getAlerts() {
		List<Alert> alerts = new ArrayList<Alert>();
		
		Cursor cursor = cr.query(AlertContentProvider.CONTENT_URI, null, null, null, AlertContentProvider.NAME);
		
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Alert alert = new Alert();
				alert.setId(cursor.getInt(cursor.getColumnIndex(AlertContentProvider._ID)));
				alert.setAlertName(cursor.getString(cursor.getColumnIndex(AlertContentProvider.NAME)));
				alert.setLatitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LATITUDE)));
				alert.setLongitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LONGITUDE)));
				alert.setRadius(cursor.getInt(cursor.getColumnIndex(AlertContentProvider.RADIUS)));
				
				alerts.add(alert);
			} while(cursor.moveToNext());
		}
		
		return alerts;
	}
}
