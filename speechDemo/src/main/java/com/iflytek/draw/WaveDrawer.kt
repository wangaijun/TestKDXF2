package com.iflytek.draw

import android.graphics.Canvas
import android.graphics.Paint
import com.iflytek.MyPip
import com.iflytek.VoiceEffectView

/**
 * Created by waj on 18-5-21.
 */
class WaveDrawer: Drawer(){
    override fun draw(c: Canvas, width: Float, height:Float,pip:MyPip,paint: Paint) {
        val cw = width*1.0F/ VoiceEffectView.size
        var i = 0
        var x = 0F
        while (i< VoiceEffectView.size){
            x += cw
            val y = height/2F
            paint.strokeWidth = pip.getDatas()[i].toFloat()*3
            c.drawLine(x-cw/2,y,x+cw/2,y,paint)
            println("waj:${x-cw/2},${x+cw/2}")
            i++
        }
    }
}