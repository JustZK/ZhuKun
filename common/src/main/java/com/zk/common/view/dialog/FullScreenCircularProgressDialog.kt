package com.zk.common.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.zk.common.R

/**
 * 全屏透明转圈圈dialog
 */
class FullScreenCircularProgressDialog(context: Context) : Dialog(context, R.style.FullScreenCircularProgressDialog) {
    //中间图片 setImageResource(R.drawable.ic_circular_progress)
    private var mRotationImage: ImageView
    //图片下方的文字
    private var mMessage: TextView
    private var mRotationAnimation: Animation

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_full_screen_circular_progress_dialog, null)
        val layout = view.findViewById<LinearLayout>(R.id.full_screen_circular_progress_dialog_ll)
        mRotationImage = view.findViewById(R.id.full_screen_circular_progress_dialog_iv)
        mMessage = view.findViewById(R.id.full_screen_circular_progress_dialog_message_tv)
        mRotationAnimation = AnimationUtils.loadAnimation(context, R.anim.load_animation)
        setCancelable(false)
        setContentView(layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT))
        if (context is Activity) {
            ownerActivity = context
        }
    }

    override fun show() {
        mRotationImage.startAnimation(mRotationAnimation);
        super.show()
    }

    override fun dismiss() {
        mRotationAnimation.cancel()
        super.dismiss()
    }

    override fun cancel() {
        mRotationAnimation.cancel()
        super.cancel()
    }

    fun setImageResource(image: Int){
        mRotationImage.setImageResource(image)
    }

    fun setMessage(msg: String){
        mMessage.setText(msg)
    }

    /**
     * 设置颜色
     * setColor(resources.getColor(R.color.red_primary))
     */
    public fun setColor(color: Int){
        mRotationImage.setColorFilter(color)
        mMessage.setTextColor(color)
    }


    /**
     * 把点击事件传到dialog下层
     */
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ownerActivity != null) {
//            ownerActivity.dispatchTouchEvent(ev)
//        }
//        return super.dispatchTouchEvent(ev)
//    }
}