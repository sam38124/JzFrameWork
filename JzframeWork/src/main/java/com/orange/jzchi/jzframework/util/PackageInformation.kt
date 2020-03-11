package com.orange.jzchi.jzframework.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.orange.jzchi.jzframework.JzActivity
import java.io.File

class PackageInformation {
    /**
         * 取得app大小
         *
         * @param context 上下文
         *
         * @return size
         */
    fun getAppSize(): Long {
        val tmpInfo =
            JzActivity.getControlInstance().getRootActivity().packageManager.getApplicationInfo(
                JzActivity.getControlInstance().getRootActivity().packageName,
                -1
            )
        val size = File(tmpInfo.sourceDir).length()
        return size
    }

    /**
         * 获取版本号
         *
         * @param context 上下文
         *
         * @return 版本号
         */
    open fun getVersionCode(): Int { //获取包管理器
        val pm: PackageManager = JzActivity.getControlInstance().getRootActivity().packageManager
        //获取包信息
        try {
            val packageInfo: PackageInfo =
                pm.getPackageInfo(JzActivity.getControlInstance().getRootActivity().packageName, 0)
            //返回版本号
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
         * 获取版本号
         *
         * @param context 上下文
         *
         * @return 版本号
         */
    open fun getVersionName(): String { //获取包管理器
        val pm: PackageManager = JzActivity.getControlInstance().getRootActivity().packageManager
        //获取包信息
        try {
            val packageInfo: PackageInfo =
                pm.getPackageInfo(JzActivity.getControlInstance().getRootActivity().packageName, 0)
            //返回版本号
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}