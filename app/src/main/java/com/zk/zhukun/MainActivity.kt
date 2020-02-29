package com.zk.zhukun

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hik.zhukun.R
import com.hik.zhukun.databinding.ActivityMainBinding
import com.zk.common.utils.RegularExpressionUtil
import com.zk.common.utils.AppVersionUtil
import com.zk.common.utils.LogUtil
import com.zk.common.utils.PingUtil


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

        binding.iv.setColorFilter(resources.getColor(R.color.blue_primary))
        binding.iv.setBackgroundColor(resources.getColor(R.color.transparent))
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
