package com.zk.common.utils

import android.content.Context
import android.content.pm.PackageManager

object AppVersionUtil {

    /**
     * 对比版本号，是否需要升级
     * @param context 上下文
     * @param newVersionName 版本名称，例如：V1.0.1.2 or 1.0.1 or V1-0-1 or 1-0-1-2  (不用区分v的大小写，和-或.的区分)
     * @param newVersionCode 若没有code则传入null或者-1
     * @return 是否需要升级
     */
    fun checkNeedUpdate(context: Context, newVersionName: String, newVersionCode: Int?): Boolean {
        val localPackage = context.packageName
        try {
            val packageInfo = context.packageManager.getPackageInfo(localPackage, 0)
            if (newVersionCode != null && newVersionCode != -1){
                return newVersionCode > packageInfo.versionCode
            } else{
                var localVersionName = packageInfo.versionName
                localVersionName = localVersionName.replace("\\D".toRegex(), "")
                var newVersionNameTemp = newVersionName.replace("\\D".toRegex(), "")
                return newVersionNameTemp > localVersionName
            }

        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return false
    }

    /**
     * 在设置界面显示版本号
     * @param context 上下文
     */
    fun appVersionNameForShow(context: Context): String{
        var versionName = ""
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = "${pi.versionName}  -  ${pi.versionCode}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }
}