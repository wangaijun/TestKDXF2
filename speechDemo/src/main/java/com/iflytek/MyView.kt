package com.iflytek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by waj on 18-5-18.
 */
class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val pip: MyPip = MyPip(size)
    val paint:Paint = Paint()
    lateinit var t:MyThread
    init {
        paint.color = Color.RED
        paint.strokeWidth = 3.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas==null) return
        val c:Canvas = canvas
        val cw = width/size
        var i = 0
        var x = cw/2
        val arr = FloatArray(size*2)
        while (i< size){
            x += cw
            arr[2*i] = x.toFloat()
            val y = pip.getDatas()[i]*3
            arr[2*i+1] = y.toFloat()
//            c.drawCircle(x.toFloat(),y.toFloat(),3.toFloat(),paint)
            i++
            println("waj:$x,$y")
        }
        c.drawLines(arr,paint)
    }

    companion object {
        const val size = 200
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        t = MyThread()
        t.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (t!=null){
            t.loop = false
        }
    }

    inner class MyThread:Thread(){
        var loop = true
        override fun run() {
            while (loop){
                Thread.sleep(16)
                postInvalidate()
            }
        }
    }
}