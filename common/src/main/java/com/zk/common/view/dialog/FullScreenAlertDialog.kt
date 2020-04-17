package com.zk.common.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import com.hik.common.R

/**
 * 全屏dialog
 */
class FullScreenAlertDialog : AlertDialog{

    constructor(context: Context) : super(context, R.style.FullscreenDialog) {
        if (context is Activity) {
            ownerActivity = context
        }
    }

    override fun show() {
        super.show()
        val layoutParams = window.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        window.decorView.setPadding(0, 0, 0, 0)
        window.attributes = layoutParams

        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
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