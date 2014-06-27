package com.sougree.amithereyet.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.location.LocationManager;

import com.sougree.amithereyet.model.Alert;
import com.sougree.amithereyet.model.AlertWithInfo;

public class DistanceHelper {
	
	private static DecimalFormat kmFormat = new DecimalFormat();
	
	static
	{
		kmFormat.setMaximumFractionDigits(2);
	}
	
	public static List<Alert> filterAlertsByDistance(List<Alert> alertList, Location currentLocation) {
		List<Alert> retVal = new ArrayList<Alert>();
		
		if(alertList != null && alertList.size() > 0 && currentLocation != null) {
			for(Alert alert: alertList) {
				retVal.add(alert);
			}
		}
		
		return retVal;
	}
	
	public static List<AlertWithInfo> getAlertsWithInfo(List<Alert> alertList, Location currentLocation) {
		List<AlertWithInfo> retVal = new ArrayList<AlertWithInfo>();
		
		if(alertList != null && alertList.size() > 0) {
			for(Alert alert: alertList) {
				AlertWithInfo alertwi = new AlertWithInfo();
				alertwi.setAlertName(alert.getAlertName());
				alertwi.setId(alert.getId());
				
				if (currentLocation != null) {
					Location alertLocation = new Location(LocationManager.GPS_PROVIDER);
					alertLocation.setLatitude(alert.getLatitude());
					alertLocation.setLongitude(alert.getLongitude());

				float distance = currentLocation.distanceTo(alertLocation);
				
					alertwi.setInfo(formatDistanceMessage(distance));
				}
				else {
					alertwi.setInfo("Location Unavailable");
				}
				retVal.add(alertwi);
			}
		}
		
		return retVal;
	}
	
	private static String formatDistanceMessage(float distance) {
		StringBuilder b = new StringBuilder();
		b.append(kmFormat.format(distance/1000));
		b.append(" Kilometers away");
		return b.toString();
	}
}
