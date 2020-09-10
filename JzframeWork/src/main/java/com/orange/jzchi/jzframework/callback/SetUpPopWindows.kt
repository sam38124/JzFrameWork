package com.orange.jzchi.jzframework.callback

import android.app.Dialog
import android.view.KeyEvent
import androidx.appcompat.widget.PopupMenu

abstract class SetUpPopWindows (var layoutId:Int){
    lateinit var dialog: PopupMenu
    abstract fun setup(rootview: PopupMenu)
    abstract fun dismess()
}
