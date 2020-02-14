package com.orange.jzchi.jzframework.callback

import android.app.Dialog
import android.view.KeyEvent

abstract class SetupDialog {
    lateinit var dialog:Dialog
    abstract fun setup(rootview: Dialog)
    abstract fun dismess()
    abstract fun keyevent(event: KeyEvent):Boolean
}
