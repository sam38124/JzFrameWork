package com.orange.jzframework

import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.Page.Page_Home

class MainActivity : JzActivity() {
    /*初次載入的代碼處理*/
    override fun ViewInit(rootview: View) {
        //設定首頁
        JzActivity.getControlInstance().setHome(Page_Home(), "Page_Home")
    }

    /*頁面切換監聽*/
    override fun ChangePageListener(tag: String, frag: Fragment) {
        //SetHome完會返回"tag","Page_Home()"的值
        Log.e("switch",tag)
    }

    /*按鈕事件監聽*/
    override fun KeyLinsten(event: KeyEvent) {

    }
}
