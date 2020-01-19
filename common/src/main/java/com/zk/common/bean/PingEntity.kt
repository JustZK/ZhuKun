package com.zk.common.bean

import android.content.Context

class PingEntity(context: Context, ip: String, pingCount: Int, pingWaitTime: Int) {
    // init
    val context = context
    val ip = ip //Ping的地址
    val pingCount = pingCount //Ping的次数
    val pingWaitTime = pingWaitTime //等待时间/设置超时的时间
    //return
    val resultBuffer: StringBuffer? = null //ping得到的数据
    val pingTime: String? = null //ping通的时间
    val result = false //结果
}