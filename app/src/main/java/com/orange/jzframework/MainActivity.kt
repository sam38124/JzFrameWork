package com.orange.jzframework
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.RootActivity
import com.orange.jzframework.Frag.Frag_Home
class MainActivity : RootActivity() {
    /*初次載入的代碼處理*/
    override fun ViewInit(rootview: View) {
        //設定首頁
        SetHome(Frag_Home(), "Frag_Home")
    }

    /*頁面切換監聽*/
    override fun ChangePageListener(tag: String, frag: Fragment) {
        //SetHome完會返回"tag","Frag_Home()"的值
    }

    /*按鈕事件監聽*/
    override fun KeyLinsten(event: KeyEvent) {

    }
}
