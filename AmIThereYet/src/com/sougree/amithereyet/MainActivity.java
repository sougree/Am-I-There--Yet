package com.sougree.amithereyet;

import com.sougree.amithereyet.dao.AlertDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Activity {

	
	private AlertDAO alertDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		alertDao = new AlertDAO(getContentResolver());
		
		AlertListAdapter adapter = new AlertListAdapter(this, alertDao.getAlerts());
		ListView list = (ListView)findViewById(R.id.alertList);
		list.setAdapter(adapter);
		
		/* Add Alert Button action to display add alert screen */
		ImageButton addAlertBtn = (ImageButton)findViewById(R.id.addAlertBtn);
		addAlertBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.sougree.amithereyet.ADD_ALERT");
				startActivity(i);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ON RESUME", "1");
		AlertListAdapter adapter = new AlertListAdapter(this, alertDao.getAlerts());
		ListView list = (ListView)findViewById(R.id.alertList);
		list.setAdapter(adapter);
		Log.d("ON RESUME", "2");
	}
	
	
	

}
