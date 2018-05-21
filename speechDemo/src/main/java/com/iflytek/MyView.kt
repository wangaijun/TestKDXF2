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
    init {
        paint.color = Color.RED
    }
    override fun onDraw(canvas: Canvas?) {
        if (canvas==null) return
        val c:Canvas = canvas
        val cw = width/ size
        val i = 0
        var x = 0
        while (i< size){
            x += cw / 2
            val y = pip.getDatas()[i]
            c.drawCircle(x.toFloat(),y.toFloat(),5.toFloat(),paint)
        }
    }

    companion object {
        const val size = 10
    }
}