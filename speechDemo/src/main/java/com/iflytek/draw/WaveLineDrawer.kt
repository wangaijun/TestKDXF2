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
        val y = height/2
        c.translate(0F,y)
        var i=0
        var x=0F
        val cw = width/size
        path.reset()
        path.moveTo(-cw,0f)
        val datas = arrayListOf<Int>()
        pip.getDatas().forEach { datas.add(it) }  //估计10为中值,从10分开,分成两部分
        var v2 = 0f
        while (i<size){
            x += i*cw
            val v1 = getNextSign(v2) * v2
            v2 = getNextSign(v1)*datas[i] * 2F
            path.quadTo(x, v1,x+cw/2, v2)
            path.moveTo(x+cw/2, v2)
            i++
        }
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3.toFloat()
//        paint.isAntiAlias = true
        c.drawPath(path,paint)
    }

    private fun getNextSign(v: Float): Int {
        return if (v>=0) -1 else 1
    }

    /**
     * val size = VoiceEffectView.size
    val cw = width/size
    var i = 0
    var x = cw/2
    val arr = FloatArray(size*4-1)
    while (i< size){
    x += cw
    val y = pip.getDatas()[i]*2F
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