package com.orange.jzchi.jzframework

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.callback.DiapathKey
import com.orange.jzchi.jzframework.callback.RootShare

abstract class JzFragement(val layout: Int) : Fragment(), DiapathKey {
    var refresh = false
    lateinit var rootview: View
    var handler = Handler()
    lateinit var act: JzActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity!! as JzActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            Log.e("rootfrag", "create${this}")
            if (::rootview.isInitialized && !refresh) {
                return rootview
            }
            rootview = inflater.inflate(layout, container, false)
            rootview.setOnClickListener { act.HideKeyBoard() }
            viewInit()


        return rootview
    }

    override fun dispatchKeyEvent(event: KeyEvent) {
    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun viewInit()
}
