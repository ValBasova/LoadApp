package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private var downloadBackgroundColor = 0
    private var loadingBackgroundColor = 0
    private var animatedProgress = 0f
    private var textColor = 0

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if (buttonState == ButtonState.Loading) {
            val valueAnimator = ValueAnimator.ofFloat(0f, widthSize.toFloat())
            valueAnimator.duration = 3000
            valueAnimator.addUpdateListener {
                animatedProgress = valueAnimator.animatedValue as Float
                invalidate()
                if (animatedProgress == widthSize.toFloat()) {
                    buttonState = ButtonState.Completed
                }
            }
            valueAnimator.start()
        }
    }


    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            downloadBackgroundColor = getColor(R.styleable.LoadingButton_downloadColor, 0)
            loadingBackgroundColor = getColor(R.styleable.LoadingButton_loadingColor, 0)
            textColor = getColor(R.styleable.LoadingButton_textColor, 0)
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = downloadBackgroundColor
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        when (buttonState) {
            ButtonState.Loading -> drawLoadingAnimation(canvas)
            ButtonState.Completed -> drawDefaultAnimation(canvas)
        }
    }

    private fun drawLoadingAnimation(canvas: Canvas?) {
        paint.color = loadingBackgroundColor
        canvas?.drawRect(0f, 0f, animatedProgress, heightSize.toFloat(), paint)

        paint.color = textColor
        val label = resources.getString(R.string.weAreLoading)
        canvas?.drawText(label, (widthSize / 2).toFloat(), (heightSize / 2).toFloat(), paint)

    }

    private fun drawDefaultAnimation(canvas: Canvas?) {
        paint.color = textColor
        val label = resources.getString(R.string.download)
        canvas?.drawText(label, (widthSize / 2).toFloat(), (heightSize / 2).toFloat(), paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}