package com.iflytek.speech.setting

import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceActivity
import android.view.Window

import com.iflytek.speech.util.SettingTextWatcher
import com.iflytek.voicedemo.R

/**
 * 听写设置界面
 */
class SpeechRecognizeSettings : PreferenceActivity(), OnPreferenceChangeListener {

    public override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferencesName = PREFER_NAME
        addPreferencesFromResource(R.xml.speech_recognize_setting)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        return true
    }

    companion object {
        const val PREFER_NAME = "com.iflytek.setting"
    }
}
