package com.sougree.amithereyet;

import com.sougree.amithereyet.dao.AlertDAO;
import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.Notification;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Activity for Add Alert screen
 * @author sopandian
 */
public class AddAlertActivity extends Activity {

	private AlertDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alert);
		
		dao = new AlertDAO(getContentResolver());
		
		String action = getIntent().getAction();
		Log.d("AddAlertActivity", action);
		int alertId = getIntent().getIntExtra(AlertContentProvider._ID, -1);
		
		boolean isEdit = "com.sougree.amithereyet.EDIT_ALERT".equals(action);
		
		/* Cancel Button Behavior */ 
		ImageButton cancelButton = (ImageButton)findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		Alert a = null;
		
		if(isEdit) {
			a = dao.getAlert(alertId);
			loadValuesToUI(a);
		}
		
		if(a == null) {
			isEdit = false; //TODO: Error condition. How to handle it better.
		}
		
		/* Add Button Behavior */
		ImageButton addButton = (ImageButton)findViewById(R.id.addButton);
		addButton.setTag(a);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText nameTxt = (EditText)findViewById(R.id.alertNameTxt);
				EditText latitudeTxt = (EditText)findViewById(R.id.latitudeTxt);
				EditText longitudeTxt = (EditText)findViewById(R.id.longitudeTxt);
				EditText radiusTxt = (EditText)findViewById(R.id.radiusTxt);
				Switch toastChk = (Switch)findViewById(R.id.toastChk);
				Switch ringChk = (Switch)findViewById(R.id.ringChk);
				
				Alert a = (Alert)v.getTag();
				
				if(a==null) {
					Uri uri = dao.addAlert(nameTxt, latitudeTxt, longitudeTxt, radiusTxt, toastChk, ringChk);
					
					if(uri != null) {
						Toast.makeText(getBaseContext(), "Alert Added!", Toast.LENGTH_SHORT).show();
					}
				}
				else {
					int rows = dao.updateAlert(a, nameTxt, latitudeTxt, longitudeTxt, radiusTxt, toastChk, ringChk);
					
					if (rows > 0) {
						Toast.makeText(getBaseContext(), "Alert Modified!", Toast.LENGTH_SHORT).show();
					}
				}
				
				finish();
			}
		});
	}
	
	private void loadValuesToUI(Alert a) {
		
		EditText nameTxt = (EditText)findViewById(R.id.alertNameTxt);
		EditText latitudeTxt = (EditText)findViewById(R.id.latitudeTxt);
		EditText longitudeTxt = (EditText)findViewById(R.id.longitudeTxt);
		EditText radiusTxt = (EditText)findViewById(R.id.radiusTxt);
		Switch toastChk = (Switch)findViewById(R.id.toastChk);
		Switch ringChk = (Switch)findViewById(R.id.ringChk);
		
		nameTxt.setText(a.getAlertName());
		latitudeTxt.setText(a.getLatitude() + "");
		longitudeTxt.setText(a.getLongitude() + "");
		radiusTxt.setText(a.getRadius() + "");
		
		for(Notification n: a.getNotifications()) {
			switch(n) {
			case RING:
				ringChk.setChecked(true);
				break;
			case TOAST:
				toastChk.setChecked(true);
				break;
			};
		}
	}
}
