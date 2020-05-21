package com.zk.zhukun

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zk.zhukun.R
import com.zk.zhukun.databinding.ActivityMainBinding
import com.zk.common.utils.AppVersionUtil
import com.zk.common.utils.LogUtil
import com.zk.common.utils.PingUtil
import com.zk.common.utils.RegularExpressionUtil
import java.lang.String


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.onClickListener = this

        LogUtil.instance.init(this)
        LogUtil.instance.d("0987654321")
        LogUtil.instance.logSwitch = true
        LogUtil.instance.d("123456")
        val a = byteArrayOf(0x32, 0x33, 0x34, 0x35)
        LogUtil.instance.d("zk", a, a.size, true)
        LogUtil.instance.d("当前版本号：", AppVersionUtil.appVersionNameForShow(this))
        LogUtil.instance.d("是否可更新1：", " " + AppVersionUtil.checkNeedUpdate(this, "1-1-1", null))
        LogUtil.instance.d("是否可更新2：", " " + AppVersionUtil.checkNeedUpdate(this, "1-0-0", null))
        LogUtil.instance.d("是否可更新3：", " " + AppVersionUtil.checkNeedUpdate(this, "1.0", null))
        LogUtil.instance.d("是否可更新4：", " " + AppVersionUtil.checkNeedUpdate(this, "1-1-1", 2))
        LogUtil.instance.d("ip：", " " + RegularExpressionUtil.isIp("10.10.10.366"))
        LogUtil.instance.d("isSubnetMask：", " " + RegularExpressionUtil.isIp("255.255.0.0"))

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
        binding.tv.setText(spanBuilder)

//        val p = FullScreenCircularProgressDialog(this);
//        p.setImageResource(R.drawable.ic_circular_progress)
//        p.setColor(resources.getColor(R.color.red_primary))
//        p.show();
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_ping_btn -> {
                Thread(Runnable {
                    var pingEntity = PingUtil.PingEntity(this,
                            "www.baidu.com", 5, 10)
                    pingEntity = PingUtil().ping(pingEntity)
                    LogUtil.instance.d("ping result：", "pingTime:${pingEntity.pingTime}\nresult:${pingEntity.result}\n${pingEntity.resultBuffer}")
                }).start()
            }
        }
    }

}
