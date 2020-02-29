package com.zk.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.hik.common.R

class SettingWithSwitchView : FrameLayout {
    private var iconBackground = 0
    private var titleRes = 0
    private var captionRes = 0
    private var defaultValue = false
    var icon: ImageView? = null
    var title: TextView? = null
    var caption: TextView? = null
    var toggle: SwitchCompat? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(
            context,
            attrs,
            android.R.attr.textViewStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        setBackgroundResource(R.drawable.ripple)
        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_setting_switch, this)
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingWithSwitchView)
//        iconBackground = a.getResourceId(R.styleable.SettingWithSwitchView_settingIconBackground, 0)
//        titleRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingTitle, 0)
//        captionRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingCaption, 0)
//        defaultValue = a.getBoolean(R.styleable.SettingWithSwitchView_settingDefaultValue, false)
        a.recycle()
    }

    override fun onFinishInflate() { //        ButterKnife.bind(this);
        icon = findViewById(R.id.icon)
        title = findViewById(R.id.title)
        caption = findViewById(R.id.caption)
        toggle = findViewById(R.id.toggle)
        icon!!.setBackgroundResource(iconBackground)
        title!!.setText(titleRes)
        caption!!.setText(captionRes)
        super.onFinishInflate()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun setChecked(checked: Boolean) {
        toggle!!.setChecked(checked)
    }

    fun getChecked(): Boolean {
        return toggle!!.isChecked()
    }

    fun setTitleText(str: String?) {
        title!!.text = str
    }

    fun setCaptionText(str: String?) {
        caption!!.text = str
    }
}