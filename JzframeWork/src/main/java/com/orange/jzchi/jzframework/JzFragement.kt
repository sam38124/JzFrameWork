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

abstract class JzFragement(val layout: Int) : Fragment(), DiapathKey {
    var refresh = false

    lateinit var rootview: View

    var handler = Handler()


    var fragId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JzActivity.fragid += 1
        fragId = JzActivity.fragid
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("rootfrag", "create${this}")
        if (haveRootView() && !refresh) {
            return rootview
        }
        rootview = inflater.inflate(layout, container, false)
        rootview.setOnClickListener { JzActivity.getControlInstance().hideKeyBoard() }
        handler.post {
            viewInit()
        }
        return rootview
    }

    fun haveRootView(): Boolean {
        return (::rootview.isInitialized)
    }

    override fun dispatchKeyEvent(event: KeyEvent) {
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun viewInit()
}
