package com.zk.common.view.progress

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Size
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.hik.common.R
import kotlin.math.abs

class FilledImageProgressBar : View {
    // 画圆环背景颜色
    @ColorRes
    private var mRingBackgroundColor: Int? = null
    // 画圆环背景图
    @DrawableRes
    private var mRingBackgroundImage: Int? = null
    // 画圆环背景图和圈的距离
    @Size
    private var mRingBackgroundImageDistance: Float? = null
    // 画圆环背景图颜色
    @ColorRes
    private var mRingBackgroundImageColor: Int? = null
    // 圆环颜色
    @ColorRes
    private var mRingColor: Int? = null
    // 圆环半径
    @Size
    private var mRingRadius: Float? = null
    // 圆环边缘宽度
    @Size
    private var mRingEdgeWidth: Float? = null
    // 字体颜色
    @ColorRes
    private var mTextColor: Int? = null
    // 总进度
    private var mTotalProgress: Float = 100f
    // 当前进度
    private var mCurrentProgress: Float = 0f
    // 显示图片还是文字
    private var mShowImage = true

    // 画圆环背景的画笔
    private var mRingBackgroundPaint: Paint? = null
    private var mRingBackgroundImagePaint: Paint? = null
    // 画圆环的画笔
    private var mRingPaint: Paint? = null
    // 画字体的画笔
    private var mTextPaint: Paint? = null
    // 字的长度
    private var mTextWidth = 0f
    // 字的高度
    private var mTextHeight = 0f

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val typeArray = context.theme.obtainStyledAttributes(attributeSet,
                R.styleable.FilledImageProgressBar, 0, 0)
        mRingBackgroundColor = typeArray.getResourceId(
                R.styleable.FilledImageProgressBar_ringBackgroundColor, R.color.md_blue_100)
        mRingBackgroundImage = typeArray.getResourceId(
                R.styleable.FilledImageProgressBar_ringBackgroundImage, R.drawable.ic_fingerprint)
        mRingBackgroundImageDistance = typeArray.getFloat(
                R.styleable.FilledImageProgressBar_ringBackgroundImageDistance, -1f)
        mRingBackgroundImageColor = typeArray.getResourceId(
                R.styleable.FilledImageProgressBar_ringBackgroundImageColor, R.color.md_blue_grey_200)
        mRingColor = typeArray.getResourceId(
                R.styleable.FilledImageProgressBar_ringColor, R.color.md_blue_200)
        mRingRadius = typeArray.getFloat(
                R.styleable.FilledImageProgressBar_ringRadius, -1f)
        mRingEdgeWidth = typeArray.getFloat(
                R.styleable.FilledImageProgressBar_ringEdgeWidth, 10f)
        mTextColor = typeArray.getResourceId(
                R.styleable.FilledImageProgressBar_textColor, R.color.md_white_1000)
        mTotalProgress = typeArray.getFloat(
                R.styleable.FilledImageProgressBar_totalProgress, 100f)
        mCurrentProgress = typeArray.getFloat(
                R.styleable.FilledImageProgressBar_currentProgress, 0f)
        mShowImage = typeArray.getBoolean(
                R.styleable.FilledImageProgressBar_showImage, true)
    }

    private fun initVariable() {
        if(mRingRadius == -1f){
            mRingRadius = width / 2f - mRingEdgeWidth!!
        }
        if (mRingBackgroundImageDistance == -1f){
            mRingBackgroundImageDistance = mRingEdgeWidth!! * 3
        }

        if (mRingPaint == null) {
            mRingPaint = Paint()
            mRingPaint!!.isAntiAlias = true
            mRingPaint!!.isDither = true
            mRingPaint!!.color = resources.getColor(mRingColor!!)
            mRingPaint!!.style = Paint.Style.STROKE
            mRingPaint!!.strokeCap = Paint.Cap.ROUND
            mRingPaint!!.strokeWidth = mRingEdgeWidth!!
        }

        if (mRingBackgroundImagePaint == null) {
            mRingBackgroundImagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mRingBackgroundImagePaint!!.isFilterBitmap = true
            mRingBackgroundImagePaint!!.isDither = true
        }

        if (mRingBackgroundPaint == null) {
            mRingBackgroundPaint = Paint()
            mRingBackgroundPaint!!.isAntiAlias = true
            mRingBackgroundPaint!!.isDither = true
            mRingBackgroundPaint!!.color = resources.getColor(mRingBackgroundColor!!)
            mRingBackgroundPaint!!.style = Paint.Style.STROKE
            mRingBackgroundPaint!!.strokeCap = Paint.Cap.ROUND
            mRingBackgroundPaint!!.strokeWidth = mRingEdgeWidth!!
        }

        if (!mShowImage && mTextPaint == null) {
            mTextPaint = Paint()
            mTextPaint!!.isAntiAlias = true
            mTextPaint!!.style = Paint.Style.FILL
            mTextPaint!!.color = resources.getColor(mRingBackgroundColor!!)
            mTextPaint!!.textSize = (mRingRadius!! / 2)
            val fm: Paint.FontMetrics = mTextPaint!!.fontMetrics
            mTextHeight = fm.descent + abs(fm.ascent)
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mCurrentProgress >= 0) {
            initVariable()

            val mRectF = RectF(width / 2 - mRingRadius!!,
                    height / 2 - mRingRadius!!,
                    width / 2 + mRingRadius!!,
                    height / 2 + mRingRadius!!)
            canvas!!.drawArc(mRectF, 0f, 360f, false, mRingBackgroundPaint!!)
            canvas.drawArc(mRectF, -90f, mCurrentProgress / mTotalProgress * 360, false, mRingPaint!!)


            if (!mShowImage) {
                val txt = "${mCurrentProgress.toInt()}%"
                mTextWidth = mTextPaint!!.measureText(txt, 0, txt.length)
                canvas.drawText(txt, width / 2 - mTextWidth / 2, height / 2 + mTextHeight / 4, mTextPaint!!)
            } else {
                val ringBackgroundImageDrawable = resources.getDrawable(mRingBackgroundImage!!)
                DrawableCompat.setTint(ringBackgroundImageDrawable, resources.getColor(mRingBackgroundImageColor!!));
                val mBitmap = ringBackgroundImageDrawable.toBitmap()
//                // 计算左边位置
//                val left = (width - mBitmap!!.width) / 2
//                // 计算上边位置
//                val top = (width - mBitmap!!.height) / 2
//            val mDestRect = Rect(left, top, left + mBitmap!!.width, top + mBitmap!!.height)
                val mDestRect = RectF( mRingBackgroundImageDistance!!,
                        mRingBackgroundImageDistance!!,
                        width - mRingBackgroundImageDistance!!,
                        height - mRingBackgroundImageDistance!!)
                canvas.drawBitmap(mBitmap!!,
                        null, mDestRect,
                        mRingBackgroundImagePaint)
            }
        }
    }

    fun setProgress(progress: Float) {
        mCurrentProgress = progress
        postInvalidate()
    }
}