package com.sougree.amithereyet;

import com.sougree.amithereyet.dao.AlertDAO;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity for Add Alert screen
 * @author sopandian
 */
public class AddAlertActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alert);
		
		/* Cancel Button Behavior */ 
		Button cancelButton = (Button)findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent("com.sougree.amithereyet.ADD_ALERT_CANCEL");
				startActivity(i);
			}
		});
		
		/* Add Button Behavior */
		Button addButton = (Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText nameTxt = (EditText)findViewById(R.id.alertNameTxt);
				EditText latitudeTxt = (EditText)findViewById(R.id.latitudeTxt);
				EditText longitudeTxt = (EditText)findViewById(R.id.longitudeTxt);
				EditText radiusTxt = (EditText)findViewById(R.id.radiusTxt);
				CheckBox toastChk = (CheckBox)findViewById(R.id.toastChk);
				CheckBox ringChk = (CheckBox)findViewById(R.id.ringChk);
				
				AlertDAO dao = new AlertDAO();
				Uri uri = dao.addAlert(getContentResolver(), nameTxt, latitudeTxt, longitudeTxt, radiusTxt, toastChk, ringChk);
				
				Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
			}
		});
	}
}
