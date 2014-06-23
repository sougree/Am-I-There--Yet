package com.sougree.amithereyet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Cursor c = getContentResolver().query(AlertContentProvider.CONTENT_URI, null, null, null, AlertContentProvider.NAME);
		
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.alert_list_item_view,
//				c, column, toView, 0);
		AlertListAdapter adapter = new AlertListAdapter(this, c);
		
		ListView list = (ListView)findViewById(R.id.alertList);
		list.setAdapter(adapter);
		
		/* Add Alert Button action to display add alert screen */
		Button addAlertBtn = (Button)findViewById(R.id.addAlertBtn);
		addAlertBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.sougree.amithereyet.ADD_ALERT");
				startActivity(i);
			}
		});
	}

}
