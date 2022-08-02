package com.orange.jzframework.dialog

import android.app.Dialog
import android.view.KeyEvent
import android.widget.Button
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.sampledialog.*

class Dia_Sample : SetupDialog(R.layout.sampledialog) {
    override fun keyevent(event: KeyEvent): Boolean {
        //按鈕事件的監聽
        //return true後會繼續執行父類別的dispathKeyevent方法
        return true
    }

    override fun setup(root: Dialog) {
        //Dialog的載入
        root.findViewById<Button>(R.id.button)
        root.button.setOnClickListener {
            JzActivity.getControlInstance().toast("click_button")
            JzActivity.getControlInstance().closeDiaLog()
        }
    }

    override fun dismess() {
        //關閉事件的監聽
    }

}