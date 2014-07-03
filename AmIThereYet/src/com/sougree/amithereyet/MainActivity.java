package com.sougree.amithereyet;

import com.sougree.amithereyet.core.AlertProcessor;
import com.sougree.amithereyet.core.DistanceHelper;
import com.sougree.amithereyet.dao.AlertDAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Activity implements LocationListener {
	
	private AlertDAO alertDao;
	private Location lastKnownLocation;
	private LocationManager locationManager;
	private AlertProcessor alertProcessor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		alertDao = new AlertDAO(getContentResolver());
		alertProcessor = new AlertProcessor(this, null, this.lastKnownLocation);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                3000,   // 3 sec
                10, this);
				
		refreshAlertList();
		
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
		refreshAlertList();
	}

	@Override
	public void onLocationChanged(Location location) {
		this.lastKnownLocation = location;
		refreshAlertList();
		alertProcessor.relocate(lastKnownLocation);
	}

	@Override
	public void onProviderDisabled(String provider) {
		this.lastKnownLocation = null;
		refreshAlertList();
		alertProcessor.relocate(lastKnownLocation);
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	
	private void refreshAlertList() {
		AlertListAdapter adapter = new AlertListAdapter(this, DistanceHelper.getAlertsWithInfo(alertDao.getAlerts(), lastKnownLocation));
		ListView list = (ListView)findViewById(R.id.alertList);
		list.setAdapter(adapter);
		alertProcessor.reload(alertDao.getAlerts());
	}
}
