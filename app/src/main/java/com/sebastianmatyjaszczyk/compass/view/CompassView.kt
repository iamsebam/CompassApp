package com.sebastianmatyjaszczyk.compass.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.sebastianmatyjaszczyk.compass.R


class CompassView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var azimuth: Float = 0f
        set(newAzimuth) {
            field = newAzimuth
            val animation = RotateAnimation(
                -currentAzimuth,
                -field,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                fillAfter = true
            }
            currentAzimuth = newAzimuth
            startAnimation(animation)
        }

    private val rect by lazy { RectF(0F, 0F, measuredWidth.toFloat(), measuredWidth.toFloat()) }

    private val stringsList by lazy { listOf(R.string.north, R.string.east, R.string.south, R.string.west) }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private var currentAzimuth = 0f

    private val directionLetterPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.directionLetterColor)
        textSize = 120F
    }

    private val northLetterPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.northLetterColor)
        textSize = 120F
    }

    private val compassDiscPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.compassDiscColor)
    }

    private val markerN2Paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.markersN2Color)
        strokeWidth = 2F
    }
    private val markerN30Paint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = ContextCompat.getColor(context, R.color.markersN30Color)
        strokeWidth = 4f
    }

    private val decorationPaint = Paint(ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.decorationRoseColor)
        strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawCompassDisc(this)
            drawDirectionsOnCompass(this)
            drawMarkers(this)
            drawDecoration(this)
        }
    }

    private fun drawCompassDisc(canvas: Canvas) {
        canvas.drawOval(rect, compassDiscPaint)
    }

    private fun drawDecoration(canvas: Canvas) {
        canvas.apply {
            for (i in 1..4) {
                drawLine(rect.centerX() - rect.width() / 3, rect.centerY(), rect.centerX() + rect.width() / 3, rect.centerY(), decorationPaint)
                rotate(45F, rect.centerX(), rect.centerY())
            }
            drawCircle(rect.centerX(), rect.centerY(), rect.width() / 10, decorationPaint)
            drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2 - 2, decorationPaint)
        }
    }

    private fun drawDirectionsOnCompass(canvas: Canvas) {
        val path = Path().apply {
            addCircle(rect.centerX(), rect.centerY(), rect.width() / 2 - 70, Path.Direction.CW)
        }
        val hOffset = -100F
        val vOffset = 60F
        canvas.apply {
            rotate(-90F, rect.centerX(), rect.centerY())
            stringsList.forEach { stringRes ->
                val paint = if (stringRes == R.string.north) northLetterPaint else directionLetterPaint
                drawTextOnPath(context.getString(stringRes), path, hOffset, vOffset, paint)
                rotate(90F, rect.centerX(), rect.centerY())
            }
        }
    }

    private fun drawMarkers(canvas: Canvas) {
        for (i in 1..180) {
            canvas.apply {
                rotate(2F, rect.centerX(), rect.centerY())
                when {
                    i % 15 == 0 && i % 45 != 0 -> drawLine(
                        rect.width() / 2 + rect.centerX() - 50,
                        rect.centerY(),
                        rect.centerX() + rect.width() / 2 - 10,
                        rect.width() / 2,
                        markerN30Paint
                    )
                    else -> drawLine(
                        rect.width() / 2 + rect.centerX() - 30,
                        rect.centerY(),
                        rect.centerX() + rect.width() / 2 - 10,
                        rect.width() / 2,
                        markerN2Paint
                    )
                }
            }
        }
    }
}