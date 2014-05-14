package com.devwoo.videoplayer.utils;

import java.util.Formatter;
import java.util.Locale;

public class VideoUtils {

	private static final String TAG = "VideoUtils";


	public static String durationFormat(long duration) {
		String result;
		final StringBuilder builder = new StringBuilder();
		final Formatter formatter = new Formatter(builder, Locale.getDefault());

		int totalSec = (int) duration / 1000;

		int secs = totalSec % 60;
		int minutes = (totalSec / 60) % 60;
		int hours = (totalSec / 3600);
		builder.setLength(0);
		if (hours > 0) {
			result = formatter.format("%02d:%02d:%02d", hours, minutes, secs)
					.toString();
			formatter.close();
			return result;
		} else {
			result = formatter.format("%02d:%02d:%02d", hours, minutes, secs)
					.toString();
			formatter.close();
			return result;
		}

	}
}