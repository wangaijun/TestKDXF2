package com.iflytek.activity

import android.os.Bundle
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceActivity
import android.view.Window
import com.iflytek.voicedemo.R

/**
 * 听写设置界面
 */
class SpeechRecognizeSettingsActivity : PreferenceActivity(), OnPreferenceChangeListener {

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
