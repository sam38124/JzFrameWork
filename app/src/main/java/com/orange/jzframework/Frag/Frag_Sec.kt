package com.orange.jzframework.Frag

import android.app.Dialog
import android.widget.Button
import com.orange.jzchi.jzframework.CallBack.Dailog_SetUp_C
import com.orange.jzchi.jzframework.RootActivity
import com.orange.jzchi.jzframework.RootFragement
import com.orange.jzframework.Dialog.Dia_Sample
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.sampledialog.*
import kotlinx.android.synthetic.main.sec.view.*

class Frag_Sec : RootFragement(R.layout.sec) {

    override fun ViewInit() {
        rootview.sampletext2.setOnClickListener {
            /*
            使用 ShowDaiLog 的方法顯示客製化Dialog
            BooleanA 決定Dialog是否可以被點擊消失
            BooleanB 決定Dialog背景是否透明
            R.layout.sampledialog 換成你的Dialog layout
            */
            act.ShowDaiLog(R.layout.sampledialog, true, false, object : Dailog_SetUp_C() {
                override fun SetUP(root: Dialog, act: RootActivity) {
                    root.findViewById<Button>(R.id.button)
                    root.button.setOnClickListener {
                        act.Toast("click_button")
                        act.DaiLogDismiss()
                    }
                }
            })
            act.ShowDaiLog(R.layout.sampledialog,true,false,Dia_Sample())
//            act.ShowDaiLog(R.layout.sampledialog,true, R.style.MyDisStyle, object : Dailog_SetUp_C() {
//                override fun SetUP(root: Dialog, act: RootActivity) {
//                    root.findViewById<Button>(R.id.button)
//                    root.button.setOnClickListener {
//                        act.Toast("click_button")
//                        act.DaiLogDismiss()
//                    }
//                }
//            })
        }
    }

}