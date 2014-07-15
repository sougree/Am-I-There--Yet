package com.sougree.amithereyet.core.notification;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.sougree.amithereyet.model.Alert;

public class RingNotifier implements Notifier {

	@Override
	public void notify(Context c, Alert a) {
		Uri ringToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		Ringtone ringTone = RingtoneManager.getRingtone(c, ringToneUri);
		ringTone.setStreamType(AudioManager.STREAM_RING);
		ringTone.play();
	}

}
