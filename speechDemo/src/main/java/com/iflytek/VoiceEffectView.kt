package com.iflytek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.content.res.TypedArray
import com.iflytek.voicedemo.R


/**
 * Created by waj on 18-5-18.
 */
class VoiceEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val pip: MyPip = MyPip(size)
    val paint:Paint = Paint()
    lateinit var t:MyThread
    init {
        context?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoiceEffectView)
            val paintColor = typedArray.getColor(R.styleable.VoiceEffectView_paintColor,Color.RED)
            paint.color = paintColor
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas==null) return
        val c:Canvas = canvas
        val cw = width*1.0F/size
        var i = 0
        var x = 0F
        while (i< size){
            x += cw
            val y = height/2F
            paint.strokeWidth = pip.getDatas()[i].toFloat()*3
            c.drawLine(x-cw/2,y,x+cw/2,y,paint)
            println("waj:${x-cw/2},${x+cw/2}")
            i++
        }
    }

    companion object {
        const val size = 600
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