package com.example.waj.testkdxf2

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Created by waj on 18-5-18.
 */
class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val pip:MyPip = MyPip(10)
    override fun onDraw(canvas: Canvas?) {
        if (canvas==null) return
        val c:Canvas = canvas

    }


}