package com.sougree.amithereyet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
