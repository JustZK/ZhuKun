package com.zk.zhukun

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zk.zhukun.databinding.ActivityMainBinding
import com.zk.common.utils.AppVersionUtil
import com.zk.common.utils.LogUtil
import com.zk.common.utils.PingUtil
import com.zk.common.utils.TimeUtil
import com.zk.common.view.dialog.FullScreenCircularProgressDialog
import java.lang.String
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mMainBinding: ActivityMainBinding
    private lateinit var mHandler: MainHandler
    private var mFullScreenCircularProgressDialog: FullScreenCircularProgressDialog? = null

    companion object {
        private const val PING_RESULT = 0x01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainBinding.onClickListener = this
        mMainBinding.mainTitleTv.text = String.format(
                resources.getString(R.string.project_name_with_version),
                AppVersionUtil.appVersionNameForShow(this)
        )
        mHandler = MainHandler(this)
        LogUtil.instance.logSwitch = true
        // 从资源获取字体大小
        val pixelSize = resources.getDimension(R.dimen.super_big_text)
        // 第一个参数:包含占位符字符串
        // 第二个可变参数:替换字符串的占位符,按数据类型填写,不然会报错
        val playCoutDown = String.format(getString(R.string.countdown), 99)
        val index = playCoutDown.indexOf(String.valueOf(99))
        // 字体颜色
        val redColors = ColorStateList.valueOf(Color.RED)
        // 使文本以指定的字体、大小、样式和颜色绘制。0表示使用默认的大小和字体
        val textAppearanceSpan = TextAppearanceSpan(null, 0, pixelSize.toInt(), redColors, null)
        // 使用SpannableStringBuilder设置字体大小和颜色
        val spanBuilder = SpannableStringBuilder(playCoutDown)
        // Parameters: what start 起始 end 结束 flags
        /*
         * Spanable中的常用常量：
         * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE --- 不包含头和尾
         * Spanned.SPAN_EXCLUSIVE_INCLUSIVE --- 不包含头但包含尾
         * Spanned.SPAN_INCLUSIVE_EXCLUSIVE --- 包含头但不包含尾
         * Spanned.SPAN_INCLUSIVE_INCLUSIVE--- 包含头和包含尾
         */
        spanBuilder.setSpan(textAppearanceSpan, index, index + String.valueOf(99).length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        mMainBinding.tv.text = spanBuilder

        mMainBinding.keyEditText.setHint("设备已经激活")
        mMainBinding.keyEditText.mIntervalLine = "-"
        mMainBinding.keyEditText.setKeyValue("1234-QWER-ASDF-ZXCV")
        mMainBinding.keyEditText.setEnable(false)

        LogUtil.instance.d("时间", TimeUtil.earlyMorningOfTheDay())
        LogUtil.instance.d("时间", TimeUtil.oldTimeOfSeconds(-2))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_setting_with_switch_test -> {
                mMainBinding.mainSettingWithSwitchTest.setChecked(!mMainBinding.mainSettingWithSwitchTest.getChecked())
            }
            R.id.main_ping_btn -> {
                if (mFullScreenCircularProgressDialog == null){
                    mFullScreenCircularProgressDialog = FullScreenCircularProgressDialog(this@MainActivity)
                    mFullScreenCircularProgressDialog!!.setColor(resources.getColor(R.color.blue_primary))
                }
                mFullScreenCircularProgressDialog!!.show()
                mFullScreenCircularProgressDialog!!.setMessage("ping......")
                Thread(Runnable {
                    var pingEntity = PingUtil.PingEntity(this,
                            "www.baidu.com", 5, 10)
                    pingEntity = PingUtil().ping(pingEntity)

                    val message = Message.obtain()
                    message.what = PING_RESULT
                    message.obj = "pingTime:${pingEntity.pingTime}\nresult:${pingEntity.result}\n${pingEntity.resultBuffer}"
                    mHandler.sendMessage(message)
                    LogUtil.instance.d("ping result：", "pingTime:${pingEntity.pingTime}\nresult:${pingEntity.result}\n${pingEntity.resultBuffer}")
                }).start()
            }
            R.id.main_setting_test -> {
                var progress = 0;
                Thread(Runnable {
                    while (progress < 100){
                        progress++
                        mMainBinding.mainFillImageProgressBar.setProgress(progress.toFloat())
                        try {
                            Thread.sleep(200)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }).start()
            }
            R.id.main_sb_1 -> {
                mMainBinding.keyEditText.warn(this, "error warning")
            }
        }
    }

    private fun handleMessage(msg: Message) {
        when (msg.what) {
            PING_RESULT -> {
                mFullScreenCircularProgressDialog!!.dismiss()
                Toast.makeText(this@MainActivity, msg.obj.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private class MainHandler(mainActivity: MainActivity) : Handler() {
        private val mainWeakReference = WeakReference(mainActivity)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mainWeakReference.get()!!.handleMessage(msg)
        }
    }

}
