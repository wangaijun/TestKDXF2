package com.iflytek

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.iflytek.voicedemo.R
import com.iflytek.voicedemo.SpeechRecognizeActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btn1.setOnClickListener{
            startActivityForResult(Intent(this,SpeechRecognizeActivity::class.java), REQUEST_VOICE_INPUT)
        }
    }

    companion object {
        const val REQUEST_VOICE_INPUT = 100
    }
}
