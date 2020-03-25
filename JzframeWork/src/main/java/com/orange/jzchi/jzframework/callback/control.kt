package com.orange.jzchi.jzframework.callback

import android.app.Dialog
import android.os.Handler
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.DiaClass
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.util.PackageInformation
import java.util.*

interface control {
    /*所有對外暴露的方法*/

    //頁面切換
    fun changePage(Translation: Fragment, tag: String, goback: Boolean)
    //設定首頁
    fun setHome(Translation: Fragment, tag: String)
    //頁面中的fragment切換
    fun changeFrag(Translation: Fragment, id: Int, tag: String, goback: Boolean)
    //透過tag取得推棧中的Fragement
    fun findFragByTag(a:String):Fragment?
    //取得現在顯示的頁面
    fun getNowPage():Fragment?
    //取得現在顯示的頁面的Tag名稱
    fun getNowPageTag():String
    //返回首頁
    fun goMenu()
    //回上一頁
    fun goBack()
    //反回tag為輸入值的頁面
    fun goBack(tag: String)
    //反回某個位置的頁面
    fun goBack(a: Int)
    //要求存取權限
    fun permissionRequest(Permissions: Array<String>, caller: permission_C, RequestCode: Int)
    //顯示客製化Dialog
    fun showDiaLog(Layout: Int, cancelable: Boolean, swip: Boolean,tag:String)
    //顯示客製化Dialog
    fun showDiaLog(Layout: Int, cancelable: Boolean, swip: Boolean, caller: SetupDialog,tag:String)
    //顯示客製化Dialog，並且自定義style
    fun showCustomDaiLog(Layout: Int, cancelable: Boolean, style: Int, caller: SetupDialog,tag:String)
    //取得tag為輸入值的Dialog
    fun getDialog(tag:String): Dialog?
    //關閉tag為輸入值的Dialog
    fun closeDiaLog(tag:String)
    //關閉所有Dialog
    fun closeDiaLog()
    //保存SharedPreferences紀錄
    fun setPro(key: String, value: Boolean)
    fun setPro(key: String, value: String)
    fun setPro(key: String, value: Int)
    fun getPro(key: String, value: String): String
    fun getPro(key: String, value: Boolean): Boolean
    fun getPro(key: String, value: Int): Int
    //清除記錄
    fun clearPro()

    //關閉整個app
    fun closeApp()
    //設定螢幕方向
    fun setOrientation(a: Int)
    //設定側邊抽屜
    fun setDrawer(frag: JzFragement)
    //打開側邊抽屜
    fun openDrawer()
    //關閉側邊抽屜
    fun closeDrawer()
    //刷新側邊抽屜(會重新跑一次viewInit方法)
    fun refreshDrawer()
    //吐司的顯示
    fun toast(a:String)
    //吐司的顯示(R.string.a)
    fun toast(a:Int)
    //取得Activity
    fun getRootActivity(): JzActivity
    //多國語言設定 範例:setLanuage(Locale("en"))
    fun setLanguage(local: Locale)
    //鍵盤隱藏
    fun hideKeyBoard()
    //下載apk
    fun apkDownload(url:String,callback:DownloadCallback)
    //打開apk
    fun openAPK()
    //app是否處於前台
    fun isFrontDesk():Boolean
    //螢幕常亮
    fun screenAlwaysOn()
    //關閉螢幕常亮
    fun cancelAlwaysOn()
    //取得app資訊
    fun getAppInformation():PackageInformation
    //重啟app
    fun restart()
    //檢查更新並返回版本號true跳轉至商店反之不跳轉，
    fun checkUpdate(a:Boolean):String?
    //前往商店
    fun goStore()
    //取得handler
    fun getHandler(): Handler
}