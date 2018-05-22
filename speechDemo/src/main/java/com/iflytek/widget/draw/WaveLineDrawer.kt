package com.iflytek.widget.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.iflytek.widget.datastructure.MyPip

/**
 * Created by waj on 18-5-22.
 */
class WaveLineDrawer: Drawer(){
    val path = Path()
    override fun draw(c: Canvas, width: Float, height: Float, pip: MyPip, paint: Paint) {
        val y = height/2
        path.reset()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.toFloat()
        paint.isAntiAlias = true
        val v = pip.getDatas().last().toFloat()
        var l = y-3*v
        var m = y
        var h = y+3*v
        val n = 6
        c.translate(-index.toFloat(),0F)
        path.moveTo(0F, m)
        path.quadTo(width/n, l, width*2/n, m)
        path.moveTo(width*2/n, m)
        path.quadTo(width*3/n, h, width*4/n, m)
        path.moveTo(width*4/n, m)
        path.quadTo(width*5/n, l, width*6/n, m)
        path.moveTo(width*6/n, m)
        path.quadTo(width*7/n, h, width*8/n, m)
        path.moveTo(width*8/n, m)
        path.quadTo(width*9/n, l, width*10/n, m)
        index += 1
        if (index>=width*4/6) index = 0
        println("waj:$index")
        c.drawPath(path, paint)
    }

    companion object {
        var index = 0
    }
}