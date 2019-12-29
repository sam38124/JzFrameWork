package com.orange.jzframework.page

import android.widget.TextView
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.R
import com.orange.jzframework.drawer.sampledrawer
import kotlinx.android.synthetic.main.activity_main.view.*

/*R.layout.activity_main替換為你的layout id*/
class Page_Home : JzFragement(R.layout.activity_main) {

    override fun viewInit() {
        /*
        Refresh預設值為false
        當Refresh為true時，下次載入會重新刷新頁面，並且重跑ViewInit的方法
        當Refresh為false時，下次載入時會保留上次的操作動作和頁面
        */
        refresh = true
        //使用下面其中一種方式取得layout元件
        rootview.findViewById<TextView>(R.id.sampletext).text = "Go next page"
        //或者
        rootview.sampletext.text = "Go next page"
        //true會將現在顯示的fragment加入可返回的推棧，按下返回鍵則會返回現在的頁面，反之false則不能返回現在的頁面
        rootview.sampletext.setOnClickListener {
            JzActivity.getControlInstance().changePage(Page_Sec(), "Page_Sec", true)
        }

    }
}