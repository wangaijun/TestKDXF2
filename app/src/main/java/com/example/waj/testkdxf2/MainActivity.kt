package com.example.waj.testkdxf2

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import com.iflytek.voicedemo.SpeechRecognizeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    var path:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener{
            startActivityForResult(Intent(this, SpeechRecognizeActivity::class.java), REQUEST_VOICE_INPUT)
        }

        btn2.setOnClickListener{
            path?.let {
                val mp = MediaPlayer()
                mp.setDataSource(Environment.getExternalStorageDirectory().toString() + "/msc/iat.wav")
                mp.prepare()
                mp.start()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode!= RESULT_OK) return
        when(requestCode){
            REQUEST_VOICE_INPUT ->{
                data?.let {
                    val txt = it.getStringExtra("txt")
                    path = it.getStringExtra("path")
                    tv1.text = txt
                }
            }
        }
    }

    companion object {
        const val REQUEST_VOICE_INPUT = 100
    }
}
