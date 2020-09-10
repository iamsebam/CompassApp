package com.sebastianmatyjaszczyk.compass.view

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.sebastianmatyjaszczyk.compass.R


class CompassView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    fun showDirectionPointer(angle: Int) {
        invalidate()
        requestLayout()
    }

    private val rect by lazy { RectF(0F, 0F, measuredWidth.toFloat(), measuredWidth.toFloat()) }

    // TODO: Clean it up
    private val stringsMap by lazy {
        HashMap<String, Int>().apply {
            put("north", R.string.north)
            put("east", R.string.east)
            put("w", R.string.north)
            put("north", R.string.north)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 100F
    }

    private val shadowPaint = Paint(0).apply {
        color = 0x101010
        maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
    }

    private val compassPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
    }

    private val markerPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {

            drawOval(rect, compassPaint)
            drawDirectionsOnCompass(this)
//            drawMarkers(this)
        }
    }

    private fun drawDirectionsOnCompass(canvas: Canvas) {
        val path = Path().apply {
            addCircle(rect.centerX(), rect.centerY(), rect.width() / 2 - 70, Path.Direction.CW)
        }
        val hOffset = -100F
        val vOffset = 40F
        canvas.drawTextOnPath("E", path, hOffset, vOffset, textPaint)
        canvas.rotate(90F, rect.centerX(), rect.centerY())
        canvas.drawTextOnPath("S", path, hOffset, vOffset, textPaint)
        canvas.rotate(90F, rect.centerX(), rect.centerY())
        canvas.drawTextOnPath("W", path, hOffset, vOffset, textPaint)
        canvas.rotate(90F, rect.centerX(), rect.centerY())
        canvas.drawTextOnPath("N", path, hOffset, vOffset, textPaint)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private fun drawMarkers(canvas: Canvas) {
        canvas.drawLine(rect.centerX(), rect.centerY() - rect.left, rect.centerX(), rect.centerY() - rect.left / 2, markerPaint)

    }
}