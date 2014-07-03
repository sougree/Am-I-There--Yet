package com.sougree.amithereyet.core;

import java.util.List;

import com.sougree.amithereyet.core.notification.NotificationFactory;
import com.sougree.amithereyet.core.notification.Notifier;
import com.sougree.amithereyet.model.Alert;

import android.content.Context;
import android.location.Location;

public class AlertProcessor {

	private Context context;
	private List<Alert> alerts;
	private Location currentLocation;
	
	public AlertProcessor(Context context, List<Alert> alerts, Location location) {
		this.context = context;
		this.alerts = alerts;
		this.currentLocation = location;
	}
	
	public void reload(List<Alert> alerts) {
		this.alerts = alerts;
		//run();
	}
	
	public void relocate(Location location) {
		this.currentLocation = location;
		run();
	}
	
	private void run() {
		if (this.currentLocation != null) {
			runOnValidLocation();
		}
	}
	
	private void runOnValidLocation() {
		for (Alert a: DistanceHelper.filterAlertsByDistance(alerts, currentLocation)) {
			for (Notifier n: NotificationFactory.getNotifiers(a.getNotifications())) {
				n.notify(context, a);
			}
		}
	}
}
