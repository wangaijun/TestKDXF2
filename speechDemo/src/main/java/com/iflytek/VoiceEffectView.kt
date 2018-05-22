package com.iflytek

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.iflytek.draw.WaveDrawer
import com.iflytek.draw.WaveLineDrawer
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
        WaveDrawer().draw(c,width.toFloat(),height.toFloat(),pip,paint)
//        WaveLineDrawer().draw(c,width.toFloat(),height.toFloat(),pip,paint)
    }

    companion object {
        const val size = 12
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