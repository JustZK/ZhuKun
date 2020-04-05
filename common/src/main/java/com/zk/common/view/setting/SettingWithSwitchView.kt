package com.zk.common.view.setting

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Size
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat
import com.hik.common.R

class SettingWithSwitchView : FrameLayout {
    @DrawableRes
    private var mIconRes: Int? = null
    @ColorRes
    private var mIconColorRes: Int? = null
    @ColorRes
    private var mIconBackgroundRes: Int? = null
    @StringRes
    private var mTitleTextRes: Int? = null
    @Size
    private var mTitleTextSizeRes: Float? = null
    @ColorRes
    private var mTitleTextColorRes: Int? = null
    @StringRes
    private var mCaptionTextRes: Int? = null
    @Size
    private var mCaptionTextSizeRes: Float? = null
    @ColorRes
    private var mCaptionTextColorRes: Int? = null

    private var mDefaultValue = false

    private var mIcon: ImageView? = null
    private var mTitle: TextView? = null
    private var mCaption: TextView? = null
    private var mToggle: SwitchCompat? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(
            context,
            attrs,
            android.R.attr.textViewStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        if (attrs != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                setBackgroundResource(R.drawable.ripple)
            }

            val inflater = LayoutInflater.from(getContext())
            inflater.inflate(R.layout.view_setting_switch, this)

            val a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingWithSwitchView)
            mIconRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingIcon, R.drawable.ic_zk_logo)
            mIconColorRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingIconColor, R.color.md_grey_600)
            mIconBackgroundRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingIconBackgroundColor, R.color.transparent)
            mTitleTextRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingTitle, R.string.this_is_the_title)
            mTitleTextSizeRes = a.getFloat(R.styleable.SettingWithSwitchView_settingTitleSize, resources.getDimension(R.dimen.sub_medium_big_text))
            mTitleTextColorRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingTitleColor, R.color.md_grey_600)
            mCaptionTextRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingCaption, R.string.this_is_the_content)
            mCaptionTextSizeRes = a.getFloat(R.styleable.SettingWithSwitchView_settingCaptionSize, resources.getDimension(R.dimen.medium_small_text))
            mCaptionTextColorRes = a.getResourceId(R.styleable.SettingWithSwitchView_settingCaptionColor, R.color.md_grey_500)
            mDefaultValue = a.getBoolean(R.styleable.SettingWithSwitchView_settingDefaultValue, false)

            a.recycle()
        }
    }

    override fun onFinishInflate() {
        mIcon = findViewById(R.id.icon)
        mTitle = findViewById(R.id.title)
        mCaption = findViewById(R.id.caption)
        mToggle = findViewById(R.id.toggle)

        mIcon!!.setImageResource(mIconRes!!)
        mIcon!!.setColorFilter(resources.getColor(mIconColorRes!!))
        mIcon!!.setBackgroundColor(resources.getColor(mIconBackgroundRes!!))

        mTitle!!.setText(mTitleTextRes!!)
//        mTitle!!.textSize = mTitleTextSizeRes!!
        mTitle!!.setTextColor(resources.getColor(mTitleTextColorRes!!))

        mCaption!!.setText(mCaptionTextRes!!)
//        mCaption!!.textSize = mCaptionTextSizeRes!!
        mCaption!!.setTextColor(resources.getColor(mCaptionTextColorRes!!))

        mToggle!!.isChecked = mDefaultValue

        super.onFinishInflate()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun setChecked(checked: Boolean) {
        mToggle!!.isChecked = checked
    }

    fun getChecked(): Boolean {
        return mToggle!!.isChecked
    }

    fun setTitleText(str: String) {
        mTitle!!.text = str
    }

    fun setCaptionText(str: String) {
        mCaption!!.text = str
    }
}