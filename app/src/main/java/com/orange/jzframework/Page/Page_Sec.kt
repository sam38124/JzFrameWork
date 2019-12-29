package com.orange.jzframework.Page

import android.app.Dialog
import android.view.KeyEvent
import android.widget.Button
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.sec.view.*

class Page_Sec : JzFragement(R.layout.sec) {

    override fun viewInit() {
        rootview.sampletext2.setOnClickListener {
            /*
            使用 ShowDaiLog 的方法顯示客製化Dialog
            cancelable 決定Dialog是否可以被點擊消失
            swipe 決定Dialog背景是否透明反之為不透明
            R.layout.sampledialog 換成你的Dialog layout
            */
            JzActivity.getControlInstance().showDiaLog(R.layout.sampledialog, true, false, object : SetupDialog {
                override fun keyevent(event: KeyEvent): Boolean {
                    //按鈕事件監聽
                    // return true後會繼續執行父類別的dispathKeyevent方法，反之攔截按鈕事件
                    return true
                }

                override fun setup(rootview: Dialog) {
                    //Dialog的載入設定
                    rootview.findViewById<Button>(R.id.button).setOnClickListener {
                        rootview.dismiss()
                    }
                }

                override fun dismess() {
                    //關閉事件的監聽
                }
            })
        }
        rootview.button2.setOnClickListener {
            JzActivity.getControlInstance().goBack()
        }
        rootview.button3.setOnClickListener {
            JzActivity.getControlInstance().changePage(Page_Third(), "Page_Third", true)
        }
    }

}