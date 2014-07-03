package com.sougree.amithereyet.core.notification;

import android.content.Context;

import com.sougree.amithereyet.model.Alert;

public interface Notifier {
	public void notify(Context c, Alert a);
}
