package com.orange.jzframework.Dialog

import android.app.Dialog
import android.widget.Button
import com.orange.jzchi.jzframework.CallBack.Dailog_SetUp_C
import com.orange.jzchi.jzframework.RootActivity
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.sampledialog.*

class Dia_Sample : Dailog_SetUp_C(){
    override fun SetUP(root: Dialog, act: RootActivity) {
        root.findViewById<Button>(R.id.button)
        root.button.setOnClickListener {
            act.Toast("click_button")
            act.DaiLogDismiss()
        }
    }
}