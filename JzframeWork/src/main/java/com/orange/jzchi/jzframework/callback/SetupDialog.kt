package com.orange.jzchi.jzframework.callback

import android.app.Dialog
import android.view.KeyEvent

interface SetupDialog {
    fun setup(rootview: Dialog)
    fun dismess()
    fun keyevent(event: KeyEvent):Boolean
}
