package com.zk.common.view.text

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ReplacementTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.Size
import androidx.annotation.StringRes
import com.zk.common.R
import java.util.*

class KeyEditText : FrameLayout {
    @StringRes
    private var mKeyHintRes: Int? = null

    @Size
    private var mKeyHintTextLayoutMarginTop: Float? = null

    @Size
    private var mKeyHintSizeRes: Float? = null

    @ColorRes
    private var mKeyHintColorRes: Int? = null

    private lateinit var mKeyEtOne: EditText
    private lateinit var mKeyEtTwo: EditText
    private lateinit var mKeyEtThree: EditText
    private lateinit var mKeyEtFour: EditText
    private lateinit var mKeyHint: TextView
    private lateinit var mKeyEtOneView: View
    private lateinit var mKeyEtTwoView: View
    private lateinit var mKeyEtThreeView: View
    private lateinit var mKeyEtFourView: View

    var mIntervalLine = "-"

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(
            context,
            attrs,
            android.R.attr.textViewStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        val inflater = LayoutInflater.from(getContext())
        inflater.inflate(R.layout.view_key_edit_text, this)

        val a = getContext().obtainStyledAttributes(attrs, R.styleable.KeyEditText)
        mKeyHintRes = a.getResourceId(R.styleable.KeyEditText_keyHintText, R.string.this_is_the_content)
        mKeyHintTextLayoutMarginTop = a.getFloat(R.styleable.KeyEditText_keyHintTextSize, resources.getDimension(R.dimen.medium_spacing))
        mKeyHintSizeRes = a.getFloat(R.styleable.KeyEditText_keyHintTextSize, resources.getDimension(R.dimen.medium_text))
        mKeyHintColorRes = a.getResourceId(R.styleable.KeyEditText_keyHintTextColor, R.color.black)


        a.recycle()
    }

    override fun onFinishInflate() {
        mKeyEtOne = findViewById(R.id.key_et_one)
        mKeyEtTwo = findViewById(R.id.key_et_two)
        mKeyEtThree = findViewById(R.id.key_et_three)
        mKeyEtFour = findViewById(R.id.key_et_four)
        mKeyEtOneView = findViewById(R.id.key_one_view)
        mKeyEtTwoView = findViewById(R.id.key_two_view)
        mKeyEtThreeView = findViewById(R.id.key_three_view)
        mKeyEtFourView = findViewById(R.id.key_four_view)
        mKeyHint = findViewById(R.id.key_hint)

        mKeyHint.setText(mKeyHintRes!!)
        mKeyHint.textSize = mKeyHintSizeRes!!
        mKeyHint.setTextColor(resources.getColor(mKeyHintColorRes!!))

        mKeyEtOne.transformationMethod = AllCapTransformationMethod(true)
        mKeyEtTwo.transformationMethod = AllCapTransformationMethod(true)
        mKeyEtThree.transformationMethod = AllCapTransformationMethod(true)
        mKeyEtFour.transformationMethod = AllCapTransformationMethod(true)


        mKeyEtOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when {
                    mKeyEtOne.length() == 0 -> {
                        mKeyEtOneView.setBackgroundColor(Color.parseColor("#666666"))
                    }
                    mKeyEtOne.length() == 4 -> {
                        mKeyEtOneView.setBackgroundColor(Color.parseColor("#666666"))
                        mKeyEtTwo.isFocusable = true
                        mKeyEtTwo.isFocusableInTouchMode = true
                        mKeyEtTwo.requestFocus()
                        var value = mKeyEtTwo.text.toString().trim()
                        if (value.isEmpty()) {
                            value = " "
                        }
                        mKeyEtTwo.setText(value)
                        mKeyEtTwo.setSelection(value.length)
                    }
                    mKeyEtOne.length() < 4 -> {
                        mKeyEtOneView.setBackgroundColor(Color.parseColor("#000000"))
                    }
                }
            }

        })

        mKeyEtTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when {
                    mKeyEtTwo.length() == 0 -> {
                        mKeyEtOne.isFocusable = true
                        mKeyEtOne.isFocusableInTouchMode = true
                        mKeyEtOne.requestFocus()
                        mKeyEtTwoView.setBackgroundColor(Color.parseColor("#666666"))
                    }
                    mKeyEtTwo.length() == 4 -> {
                        mKeyEtTwoView.setBackgroundColor(Color.parseColor("#666666"))
                        mKeyEtThree.isFocusable = true
                        mKeyEtThree.isFocusableInTouchMode = true
                        mKeyEtThree.requestFocus()
                        var value = mKeyEtThree.text.toString().trim()
                        if (value.isEmpty()) {
                            value = " "
                        }
                        mKeyEtThree.setText(value)
                        mKeyEtThree.setSelection(value.length)
                    }
                    mKeyEtTwo.length() == 2 -> {
                        if (mKeyEtTwo.text.trim().length == 1) {
                            mKeyEtTwo.setText(mKeyEtTwo.text.trim())
                            mKeyEtTwo.setSelection(1)
                        }
                    }
                    mKeyEtTwo.length() < 4 -> {
                        mKeyEtTwoView.setBackgroundColor(Color.parseColor("#000000"))
                    }
                }
            }

        })

        mKeyEtThree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when {
                    mKeyEtThree.length() == 0 -> {
                        mKeyEtTwo.isFocusable = true
                        mKeyEtTwo.isFocusableInTouchMode = true
                        mKeyEtTwo.requestFocus()
                        mKeyEtThreeView.setBackgroundColor(Color.parseColor("#666666"))
                    }
                    mKeyEtThree.length() == 4 -> {
                        mKeyEtThreeView.setBackgroundColor(Color.parseColor("#666666"))
                        mKeyEtFour.isFocusable = true
                        mKeyEtFour.isFocusableInTouchMode = true
                        mKeyEtFour.requestFocus()
                        var value = mKeyEtFour.text.toString().trim()
                        if (value.isEmpty()) {
                            value = " "
                        }
                        mKeyEtFour.setText(value)
                        mKeyEtFour.setSelection(value.length)
                    }
                    mKeyEtThree.length() == 2 -> {
                        if (mKeyEtThree.text.trim().length == 1) {
                            mKeyEtThree.setText(mKeyEtThree.text.trim())
                            mKeyEtThree.setSelection(1)
                        }
                    }
                    mKeyEtThree.length() < 4 -> {
                        mKeyEtThreeView.setBackgroundColor(Color.parseColor("#000000"))
                    }
                }
            }

        })

        mKeyEtFour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when {
                    mKeyEtFour.length() == 0 -> {
                        mKeyEtThree.isFocusable = true
                        mKeyEtThree.isFocusableInTouchMode = true
                        mKeyEtThree.requestFocus()
                        mKeyEtFourView.setBackgroundColor(Color.parseColor("#666666"))
                    }
                    mKeyEtFour.length() == 4 -> {
                        mKeyEtFourView.setBackgroundColor(Color.parseColor("#666666"))
                    }
                    mKeyEtFour.length() == 2 -> {
                        if (mKeyEtFour.text.trim().length == 1) {
                            mKeyEtFour.setText(mKeyEtFour.text.trim())
                            mKeyEtFour.setSelection(1)
                        }
                    }
                    mKeyEtFour.length() < 4 -> {
                        mKeyEtFourView.setBackgroundColor(Color.parseColor("#000000"))
                    }
                }
            }

        })

        super.onFinishInflate()
    }

    fun getKeyValue(): String?{
        val one = mKeyEtOne.text.trim().toString().toUpperCase(Locale.getDefault())
        val two = mKeyEtTwo.text.trim().toString().toUpperCase(Locale.getDefault())
        val three = mKeyEtThree.text.trim().toString().toUpperCase(Locale.getDefault())
        val four = mKeyEtFour.text.trim().toString().toUpperCase(Locale.getDefault())
        if (one.isNotEmpty() && two.isNotEmpty() && three.isNotEmpty() && four.isNotEmpty()){
            return "$one$mIntervalLine$two$mIntervalLine$three$mIntervalLine$four"
        }
        return null
    }

    fun setKeyValue(key: String) : Boolean{
        if (key.length < 16) return false
        else if (key.length == 16) {
            mKeyEtOne.setText(key.substring(0,4))
            mKeyEtTwo.setText(key.substring(4,8))
            mKeyEtThree.setText(key.substring(8,12))
            mKeyEtFour.setText(key.substring(12,16))
        } else {
            val keys = key.split(mIntervalLine).toTypedArray()
            if (keys.size == 4){
                mKeyEtOne.setText(keys[0])
                mKeyEtTwo.setText(keys[1])
                mKeyEtThree.setText(keys[2])
                mKeyEtFour.setText(keys[3])
            } else {
                return false
            }
        }

        return true
    }

    fun setEnable(enable : Boolean){
        mKeyEtOne.isEnabled = enable
        mKeyEtTwo.isEnabled = enable
        mKeyEtThree.isEnabled = enable
        mKeyEtFour.isEnabled = enable
    }

    fun getEnable(): Boolean{
        return mKeyEtOne.isEnabled && mKeyEtTwo.isEnabled && mKeyEtThree.isEnabled && mKeyEtFour.isEnabled
    }

    // 为组件设置一个抖动效果
    fun warn(context: Context, str: String) {
        val shake = AnimationUtils.loadAnimation(context,
                R.anim.shake)
        mKeyEtOne.startAnimation(shake)
        mKeyEtTwo.startAnimation(shake)
        mKeyEtThree.startAnimation(shake)
        mKeyEtFour.startAnimation(shake)
        // 改变view的颜色
        mKeyEtOneView.setBackgroundColor(Color.parseColor("#F34B56"))
        mKeyEtTwoView.setBackgroundColor(Color.parseColor("#FF0033"))
        mKeyEtThreeView.setBackgroundColor(Color.parseColor("#FF0033"))
        mKeyEtFourView.setBackgroundColor(Color.parseColor("#FF0033"))

        mKeyHint.text = str
        mKeyHint.setTextColor(resources.getColor(R.color.md_red_600))
    }

    fun setHint(str: String){
        mKeyHint.text = str
        mKeyHint.setTextColor(resources.getColor(mKeyHintColorRes!!))
    }

    // 转大写
    class AllCapTransformationMethod(needUpper: Boolean) : ReplacementTransformationMethod() {
        private val lower = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        private val upper = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        private var allUpper = false
        override fun getOriginal(): CharArray {
            return if (allUpper) {
                lower
            } else {
                upper
            }
        }

        override fun getReplacement(): CharArray {
            return if (allUpper) {
                upper
            } else {
                lower
            }
        }

        init {
            allUpper = needUpper
        }
    }
}