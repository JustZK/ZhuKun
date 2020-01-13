package com.zk.common.utils

import java.util.regex.Pattern

object RegularExpressionUtil {

    //IP正则表达式
    fun isIp(ip: String): Boolean {
        val str = ("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]ip|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)")
        return ip.matches(str.toRegex())
    }

    //子网掩码正则表达式
    fun isSubnetMask(subnetMask: String): Boolean {
        val str = "^((128|192)|2(24|4[08]|5[245]))(\\.(0|(128|192)|2((24)|(4[08])|(5[245])))){3}$"
        return subnetMask.matches(str.toRegex())
    }

    //数字
    fun isNumber(number: String): Boolean {
        val str = "^[0-9]*$"
        return number.matches(str.toRegex())
    }

    //MAC地址的正则表达式
    fun isMac(mac: String): Boolean {
        val str = "([0-9a-fA-F]{2})(([/\\\\s:-][0-9a-fA-F]{2}){5})"
        return mac.matches(str.toRegex())
    }

    //手机号正则表达式
    fun isPhone(phoneNumber: String): Boolean {
        val str = "(?:^1[3456789]|^9[28])\\d{9}$"
        return phoneNumber.matches(str.toRegex())
    }
}