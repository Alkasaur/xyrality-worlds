package com.xirality.worlds.utils;

import android.text.TextUtils;

public final class SafeValueUtils {
	private static final String EMPTY_STR = "";

	public static String safeString (String string) {
		if (TextUtils.isEmpty(string)) {
			return EMPTY_STR;
		} else {
			return string;
		}
	}
}
