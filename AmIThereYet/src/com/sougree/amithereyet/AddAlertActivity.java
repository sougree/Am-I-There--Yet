package com.sougree.amithereyet;

import com.sougree.amithereyet.dao.AlertDAO;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
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
		
		/* Cancel Button Behavior */ 
		ImageButton cancelButton = (ImageButton)findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Intent i = new Intent("com.sougree.amithereyet.ADD_ALERT_CANCEL");
				//startActivity(i);
				finish();
			}
		});
		
		/* Add Button Behavior */
		ImageButton addButton = (ImageButton)findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText nameTxt = (EditText)findViewById(R.id.alertNameTxt);
				EditText latitudeTxt = (EditText)findViewById(R.id.latitudeTxt);
				EditText longitudeTxt = (EditText)findViewById(R.id.longitudeTxt);
				EditText radiusTxt = (EditText)findViewById(R.id.radiusTxt);
				Switch toastChk = (Switch)findViewById(R.id.toastChk);
				Switch ringChk = (Switch)findViewById(R.id.ringChk);
				
				Uri uri = dao.addAlert(nameTxt, latitudeTxt, longitudeTxt, radiusTxt, toastChk, ringChk);
				
				if(uri != null) {
					Toast.makeText(getBaseContext(), "Alert Added!", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}
}
