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

    lateinit var act: JzActivity

    var fragId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity!! as JzActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        JzActivity.fragid+=1
        fragId=JzActivity.fragid
            Log.e("rootfrag", "create${this}")
            if (::rootview.isInitialized && !refresh) {
                if(rootview.parent != null){
                    val parentView = rootview.parent as ViewGroup
                    parentView.endViewTransition(rootview);//主动调用清除动画
                    parentView.removeView(rootview);
                }
                return rootview
            }
            rootview = inflater.inflate(layout, container, false)
            rootview.setOnClickListener { act.HideKeyBoard() }
        viewInit()
        return rootview
    }

    fun haveRootView():Boolean{
        return (::rootview.isInitialized)
    }

    override fun dispatchKeyEvent(event: KeyEvent) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (haveRootView()&&rootview.parent != null) {
            val parentView = rootview.parent as ViewGroup
            parentView.endViewTransition(rootview)
            parentView.clearAnimation()
            parentView.removeView(parentView)

        }
    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun viewInit()
}
