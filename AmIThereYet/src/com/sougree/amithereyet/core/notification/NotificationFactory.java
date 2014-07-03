package com.sougree.amithereyet.core.notification;

import java.util.ArrayList;
import java.util.List;

import com.sougree.amithereyet.model.Notification;

public final class NotificationFactory {
	private NotificationFactory() {
		//To make this class uninstantiatable
	}
	
	public static Notifier getNotifier(Notification n) {
		Notifier ret = null;
		
		switch(n) {
		
		case TOAST:
			ret = new ToastNotifier();
		break;
		
		case RING:
			ret = new RingNotifier();
		break;
		
		};
		
		return ret;
	}
	
	public static List<Notifier> getNotifiers(List<Notification> nList) {
		List<Notifier> retList = new ArrayList<Notifier>();
		
		for(Notification n: nList) {
			Notifier notifier = getNotifier(n);
			if (notifier!=null) {
				retList.add(notifier);
			}
		}
		
		return retList;
	}
}
