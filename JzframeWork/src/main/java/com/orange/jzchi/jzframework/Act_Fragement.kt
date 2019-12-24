package com.orange.jzchi.jzframework

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orange.jzchi.jzframework.CallBack.DiapathKey
import com.orange.jzchi.jzframework.CallBack.RootShare
import com.orange.jzchi.jzframework.tool.LanguageUtil

abstract class Act_Fragement(val layout: Int, val fragid: Int) : Fragment(), DiapathKey {
    val LOCALE_ENGLISH = "en"
    val LOCALE_CHINESE = "zh"
    val LOCALE_TAIWAIN = "tw"
    val LOCALE_ITALIANO = "it"
    val LOCALE_DE = "de"
    lateinit var LastTemp: Fragment
    var LastTag = ""
    lateinit var act:RootActivity
    lateinit var rootview: View
    lateinit var rootshare:RootShare
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity!! as RootActivity
        rootshare= RootShare(act)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("rootfrag", "create")
        if (::rootview.isInitialized) {
            if (::LastTemp.isInitialized) {
                act.ChangePage(LastTemp, fragid, LastTag, false)
            }
            return rootview
        }
        rootview = inflater.inflate(layout, container, false)
        rootview.setOnClickListener { act.HideKeyBoard() }
        Laninit()
        ViewInit()
        return rootview
    }

    open fun GoMenu() {
        //返回首页,清除栈顶
        act.supportFragmentManager.popBackStack(null, 1)
    }

    open fun SetLan(value: String) {
        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString("Lan", value).commit()
        Laninit()
        ViewInit()
    }

    open fun Laninit() {
        val profilePreferences = act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        when (profilePreferences.getString("Lan", LOCALE_ENGLISH)) {
            LOCALE_ENGLISH -> {
                LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ENGLISH);
            }
            LOCALE_CHINESE -> {
                LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_CHINESE);
            }
            LOCALE_TAIWAIN -> {
                LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_TAIWAIN);
            }
            LOCALE_ITALIANO -> {
                LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ITALIANO);
            }
            LOCALE_DE -> {
                LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_DE);
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent) {

    }

    open fun Toast(a: String) {
        act.Toast(a)
    }

    open fun ChangeFrage(Translation: Fragment, tag: String, goback: Boolean) {
        act.ChangePage(Translation, fragid, tag, goback)
        LastTag=tag
        LastTemp = Translation
    }

    override fun onResume() {
        super.onResume()
    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun ViewInit()

}
