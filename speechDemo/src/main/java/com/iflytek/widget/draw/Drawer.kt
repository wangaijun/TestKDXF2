package com.iflytek.widget.draw

import android.graphics.Canvas
import android.graphics.Paint
import com.iflytek.widget.datastructure.MyPip

/**
 * Created by waj on 18-5-21.
 */
abstract class Drawer{
    abstract fun draw(c: Canvas, width: Float, height:Float, pip: MyPip, paint: Paint)
}