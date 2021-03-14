package com.zk.common.view.text

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.zk.common.R

class KeyEditText : FrameLayout {
    @StringRes
    private var mKeyHintRes: Int? = null

    private lateinit var mKeyEtOne: EditText
    private lateinit var mKeyEtTwo: EditText
    private lateinit var mKeyEtThree: EditText
    private lateinit var mKeyEtFour: EditText
    private lateinit var mKeyHint: TextView

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
        mKeyHintRes = a.getResourceId(R.styleable.KeyEditText_keyHint, R.string.this_is_the_content)


        a.recycle()
    }

    override fun onFinishInflate() {
        mKeyEtOne = findViewById(R.id.key_et_one)
        mKeyEtTwo = findViewById(R.id.key_et_two)
        mKeyEtThree = findViewById(R.id.key_et_three)
        mKeyEtFour = findViewById(R.id.key_et_four)
        mKeyHint = findViewById(R.id.key_hint)

        mKeyHint.setText(mKeyHintRes!!)

        super.onFinishInflate()
    }
}