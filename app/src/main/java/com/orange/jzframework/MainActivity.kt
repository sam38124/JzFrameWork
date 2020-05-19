package com.orange.jzframework

import android.app.Dialog
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.callback.DownloadCallback
import com.orange.jzchi.jzframework.callback.permission_C
import com.orange.jzframework.drawer.sampledrawer
import com.orange.jzframework.page.Page_Home
import java.util.jar.Manifest

class MainActivity : JzActivity() {
    /*初次載入的代碼處理*/
    override fun viewInit(rootview: View) {
        //設定首頁
        JzActivity.getControlInstance().setHome(Page_Home(), "Page_Home")
        JzActivity.getControlInstance().setDrawer(sampledrawer())
    }

    /*頁面切換監聽*/
    override fun changePageListener(tag: String, frag: Fragment) {
        //SetHome完會返回"tag","Page_Home()"的值
        Log.e("switch", tag)
    }

    /*按鈕事件監聽*/
    override fun keyEventListener(event: KeyEvent): Boolean {
        return true
    }

    override fun savedInstanceAble(): Boolean {
        return true
    }

    override fun dialogLinstener(dialog: Dialog, tag: String) {

    }
}
