package com.sougree.amithereyet.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Alert model
 * @author Soundra Pandian
 */
public class Alert {
	
	/**
	 * Name given for an alert. This would appear in the notifications.
	 */
	protected String alertName;
	
	/**
	 * Latitude of the center point
	 */
	protected float latitude;
	
	/**
	 * Longitude of the center point
	 */
	protected float longitude;
	
	/**
	 * Radius of the location in kilometers
	 */
	protected int radius;
	
	/**
	 * List of notifications enabled for this alert
	 */
	protected List<Notification> notifications = new ArrayList<Notification>();

	/**
	 * @return the alertName
	 */
	public String getAlertName() {
		return alertName;
	}

	/**
	 * @param alertName the alertName to set
	 */
	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the notifications
	 */
	public List<Notification> getNotifications() {
		return notifications;
	}
	
	/**
	 * @param aNotification
	 */
	public void addNotification(Notification aNotification) {
		if(aNotification != null) this.notifications.add(aNotification);
	}
	
	/**
	 * Saves this model to database
	 */
	public void save() {
		Log.d("ALERT - Name ", this.alertName);
		Log.d("ALERT - Lat ", this.latitude + "");
		Log.d("ALERT - Lon ", this.longitude +"");
		Log.d("ALERT - Rad ", this.radius + "");
		Log.d("ALERT - Notifications ", this.notifications.toString());
	}
}
