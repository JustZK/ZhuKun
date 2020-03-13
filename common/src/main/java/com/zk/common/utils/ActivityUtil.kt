package com.zk.common.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri

object ActivityUtil {

    /**
     * 判断该activity是否在最上层
     * @param activityName 包名 like：com.zk.common.utils.ActivityUtil
     */
    fun isTopActivity(context: Context, activityName: String): Boolean {
        LogUtil.instance.d("isTopActivity")
        var result = false
        try {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val cn = am.getRunningTasks(1)[0].topActivity
            LogUtil.instance.d("para:$activityName")
            LogUtil.instance.d("TopActivity:${cn.className}")
            result = cn.className == activityName
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.instance.d(e.message.toString())
        }
        return result
    }

    /**
     * 获取正在运行桌面包名
     */
    fun getLauncherPackageName(context: Context): String? {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val res = context.packageManager.resolveActivity(intent, 0)
        return if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            null
        } else res.activityInfo.packageName
    }

    /**
     * 卸载 APK。
     * @param packageName 应用的包名
     */
    fun uninstallApk(context: Context, packageName: String) {
        val packageURI = Uri.parse("package:$packageName")
        val intent = Intent(Intent.ACTION_DELETE, packageURI)
        context.startActivity(intent)
    }
}