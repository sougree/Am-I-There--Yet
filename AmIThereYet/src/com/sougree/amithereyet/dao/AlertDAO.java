package com.sougree.amithereyet.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sougree.amithereyet.AlertContentProvider;
import com.sougree.amithereyet.R;
import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.Notification;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
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
		values.put(AlertContentProvider.NOTIFICATIONS, getJSON(alert.getNotifications()));
		Uri uri = cr.insert(AlertContentProvider.CONTENT_URI, values);
		
		return uri;
	}
	
	public int updateAlert(Alert alert, EditText nameTxt, EditText latTxt, EditText lonTxt, EditText radTxt, Switch... switches) {
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
		values.put(AlertContentProvider.NOTIFICATIONS, getJSON(alert.getNotifications()));
		Uri uri = Uri.withAppendedPath(AlertContentProvider.CONTENT_URI, alert.getId()+"");
		String whereClause = AlertContentProvider._ID + " = " + alert.getId();
		return cr.update(uri, values, whereClause, null);
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
				alert.getNotifications().addAll(getNotificationsArray(cursor.getString(cursor.getColumnIndex(AlertContentProvider.NOTIFICATIONS))));
				alerts.add(alert);
			} while(cursor.moveToNext());
		}
		
		return alerts;
	}
	
	public Alert getAlert(int dbPK) {
		
		Alert alert = null;
		String filter = AlertContentProvider._ID + " = " + dbPK;
		Cursor cursor = cr.query(AlertContentProvider.CONTENT_URI, null, filter, null, AlertContentProvider.NAME);
		
		if (cursor != null && cursor.moveToFirst()) {

			alert = new Alert();
			alert.setId(cursor.getInt(cursor.getColumnIndex(AlertContentProvider._ID)));
			alert.setAlertName(cursor.getString(cursor.getColumnIndex(AlertContentProvider.NAME)));
			alert.setLatitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LATITUDE)));
			alert.setLongitude(cursor.getFloat(cursor.getColumnIndex(AlertContentProvider.LONGITUDE)));
			alert.setRadius(cursor.getInt(cursor.getColumnIndex(AlertContentProvider.RADIUS)));
			alert.getNotifications().addAll(getNotificationsArray(cursor.getString(cursor.getColumnIndex(AlertContentProvider.NOTIFICATIONS))));
		}
		
		return alert;
	}
	
	private List<Notification> getNotificationsArray(String notifJSONFromDB) {
		List<Notification> retlist = new ArrayList<Notification>();

		Log.d("JSON - IP ", notifJSONFromDB);
		try {
			JSONObject json = new JSONObject(notifJSONFromDB);
			JSONArray notifArray = json.getJSONArray("NOTIF");
			
			for(int i = 0; i < notifArray.length(); i++) {
				Notification n = getNotification(notifArray.optString(i));
				retlist.add(n);
			}
		} 
		catch (JSONException e) {
			Log.d("JSON - EX ", e.getMessage());
			//e.printStackTrace();
		}
		Log.d("JSON - OP ", retlist.toString());
		return retlist;
	}
	
	private String getJSON(List<Notification> notifs) {
		String ret = "";
		
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		for(Notification n: notifs) {
			array.put(n.toString());
		}
		
		try {
			json.put("NOTIF", array);
			ret = json.toString();
		} 
		catch (JSONException e) {
			//TODO: Handle it :(
		}
		
		return ret;
	}
	
	private Notification getNotification(String notif) {
		if("TOAST".equals(notif)) {
			return Notification.TOAST;
		}
		else if ("RING".equals(notif)) {
			return Notification.RING;
		}
		else {
			return null;
		}
	}
}
