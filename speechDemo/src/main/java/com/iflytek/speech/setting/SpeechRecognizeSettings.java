package com.iflytek.speech.setting;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.Window;

import com.iflytek.speech.util.SettingTextWatcher;
import com.iflytek.voicedemo.R;

/**
 * 听写设置界面
 */
public class SpeechRecognizeSettings extends PreferenceActivity implements OnPreferenceChangeListener {
	
	public static final String PREFER_NAME = "com.iflytek.setting";

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesName(PREFER_NAME);
		addPreferencesFromResource(R.xml.speech_recognize_setting);
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		return true;
	}
}
