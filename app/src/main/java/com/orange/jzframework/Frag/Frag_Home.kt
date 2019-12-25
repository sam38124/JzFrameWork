package com.orange.jzframework.Frag

import android.widget.TextView
import com.orange.jzchi.jzframework.RootFragement
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.activity_main.view.*

/*R.layout.activity_main替換為你的layout id*/
class Frag_Home : RootFragement(R.layout.activity_main) {

    /*初次載入的代碼處理*/
    override fun ViewInit() {
        /*
        Refresh預設值為false
        當Refresh為true時，下次載入會重新刷新頁面，並且重跑ViewInit的方法
        當Refresh為false時，下次載入時會保留上次的操作動作和頁面
        */
        Refresh = true
        //使用下面其中一種方式取得layout元件
        rootview.findViewById<TextView>(R.id.sampletext).text = "Go next page"
        //或者
        rootview.sampletext.text = "Go next page"
        //true會將現在顯示的fragment加入可返回的推棧，按下返回鍵則會返回現在的頁面，反之false則不能返回現在的頁面
//使用SetHome的Function進行首頁的設定和更改
        rootview.sampletext.setOnClickListener {
            act.ChangePage(Frag_Sec(),"Frag_Sec",true)
        }
    }
}