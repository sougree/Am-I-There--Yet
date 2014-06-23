package com.sougree.amithereyet.dao;

import com.sougree.amithereyet.AlertContentProvider;
import com.sougree.amithereyet.R;
import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.Notification;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * DAO to CURD alert
 * @author Soundra Pandian
 */
public class AlertDAO {
	
	/**
	 * Add Alert
	 * @param nameTxt
	 * @param latTxt
	 * @param lonTxt
	 * @param radTxt
	 * @param checkboxes
	 */
	public Uri addAlert(ContentResolver cr, EditText nameTxt, EditText latTxt, EditText lonTxt, EditText radTxt, CheckBox... checkboxes) {
		Alert alert = new Alert();
		alert.setAlertName(nameTxt.getText().toString());
		alert.setLatitude(Float.parseFloat(latTxt.getText().toString()));
		alert.setLongitude(Float.parseFloat(lonTxt.getText().toString()));
		alert.setRadius(Integer.parseInt(radTxt.getText().toString()));
		
		for (CheckBox chk: checkboxes) {
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
}
