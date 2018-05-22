package com.iflytek.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.iflytek.MyPip
import com.iflytek.VoiceEffectView

/**
 * Created by waj on 18-5-22.
 */
class WaveLineDrawer: Drawer(){
    val path = Path()

    override fun draw(c: Canvas, width: Float, height: Float, pip: MyPip, paint: Paint) {
        val size = VoiceEffectView.size
        path.reset()
        val y = height/2
        var i=0
        var x=0F
        val cw = width/size
        path.moveTo(-cw,y)
        val datas = pip.getDatas()
        while (i<size/2){
            x += i*2*cw
            path.rQuadTo(x, datas[i*2]*3F,x+cw, datas[i*2+1]*3F)
            i++
        }
        c.drawPath(path,paint)
    }

    /**
     * val size = VoiceEffectView.size
    val cw = width/size
    var i = 0
    var x = cw/2
    val arr = FloatArray(size*4-1)
    while (i< size){
    x += cw
    val y = pip.getDatas()[i]*3F
    if (i==0) {
    arr[4*i] = x
    arr[4*i + 1] = y
    }
    else if (i== size-1){
    arr[4*(i-1) + 2] = x
    arr[4*(i-1) + 3] = y
    }
    else{
    arr[4*(i-1) + 2] = x
    arr[4*(i-1) + 3] = y
    arr[4*i] = x
    arr[4*i + 1] = y
    }

    i++
    println("waj:$x,$y")
    }
    c.drawLines(arr,paint)
     * */
}