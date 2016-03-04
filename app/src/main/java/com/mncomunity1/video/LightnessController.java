package com.mncomunity1.video;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings.System;
import android.view.WindowManager;
import android.widget.Toast;

public class LightnessController {

	public static boolean isAutoBrightness(Activity act) {
		boolean automicBrightness = false;
		ContentResolver aContentResolver = act.getContentResolver();
		try {
			automicBrightness = System.getInt(aContentResolver,
					System.SCREEN_BRIGHTNESS_MODE) == System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		} catch (Exception e) {
			Toast.makeText(act, "Error auto lightness", Toast.LENGTH_SHORT).show();
		}
		return automicBrightness;
	}

	public static void setLightness(Activity act, int value) {
		try {
			System.putInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, value);
			WindowManager.LayoutParams lp = act.getWindow().getAttributes();
			lp.screenBrightness = (value <= 0 ? 1 : value) / 255f;
			act.getWindow().setAttributes(lp);
		} catch (Exception e) {
			Toast.makeText(act, "Error set lightness", Toast.LENGTH_SHORT).show();
		}
	}

	public static int getLightness(Activity act) {
		return System.getInt(act.getContentResolver(), System.SCREEN_BRIGHTNESS, -1);
	}

	public static void stopAutoBrightness(Activity activity) {
		System.putInt(activity.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE,
				System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	public static void startAutoBrightness(Activity activity) {
		System.putInt(activity.getContentResolver(),
				System.SCREEN_BRIGHTNESS_MODE,
				System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}
}
