package com.sougree.amithereyet.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sougree.amithereyet.core.notification.NotificationFactory;
import com.sougree.amithereyet.core.notification.Notifier;
import com.sougree.amithereyet.model.Alert;

import android.content.Context;
import android.location.Location;

public class AlertProcessor {

	private Context context;
	private List<Alert> alerts;
	private Location currentLocation;
	private Set<Alert> toastAlerts = new HashSet<Alert>();
	private Set<Alert> ringAlert = new HashSet<Alert>();
	private Set<Alert> alerted = new HashSet<Alert>();
	
	public AlertProcessor(Context context, List<Alert> alerts, Location location) {
		this.context = context;
		this.alerts = alerts;
		this.currentLocation = location;
	}
	
	public void reload(List<Alert> alerts) {
		this.alerts = alerts;
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
		
		List<Alert> filteredList = DistanceHelper.filterAlertsByDistance(alerts, currentLocation); 
		
		for (Alert a: filteredList) {
			
			if(!alerted.contains(a)) {
				for (Notifier n: NotificationFactory.getNotifiers(a.getNotifications())) {
					n.notify(context, a);
				}
				alerted.add(a);
			}
		}
		
		for (Alert a: this.alerts) {
			if (!filteredList.contains(a)) {
				this.alerted.remove(a);
			}
		}
	}
}
