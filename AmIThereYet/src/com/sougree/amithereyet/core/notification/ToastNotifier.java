package com.sougree.amithereyet.core.notification;

import android.content.Context;
import android.widget.Toast;

import com.sougree.amithereyet.model.Alert;

public class ToastNotifier implements Notifier {

	@Override
	public void notify(Context c, Alert a) {
		Toast.makeText(c, makeText(a) , Toast.LENGTH_LONG).show();
	}

	private String makeText(Alert a) {
		StringBuilder b = new StringBuilder();
		b.append("You are with in ");
		b.append(a.getRadius());
		b.append(" kilometers of ");
		b.append(a.getAlertName());
		return b.toString();
	}
}
