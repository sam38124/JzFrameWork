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

abstract class RootFragement(val layout:Int) : Fragment(), DiapathKey {
    lateinit var rootshare:RootShare
    val LOCALE_ENGLISH="en"
    val LOCALE_CHINESE="zh"
    val LOCALE_TAIWAIN="tw"
    val LOCALE_ITALIANO="it"
    val LOCALE_DE="de"
    var Fraging: Fragment? = null
    var FragName = ""
    var Refresh=false
    lateinit var rootview:View
    var handler= Handler()
    lateinit var act: RootActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act=activity!! as RootActivity
        rootshare=RootShare(act)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("rootfrag","create")
        Laninit()
        if(::rootview.isInitialized&&!Refresh){return rootview}
        rootview=inflater.inflate(layout, container, false)
        rootview.setOnClickListener { act.HideKeyBoard() }
        ViewInit()
        return rootview
    }
    open fun Refresh_Onresume(boolean: Boolean){Refresh=true}
    open fun GoMenu(){
        //返回首页,清除栈顶
        act.supportFragmentManager.popBackStack(null,1)
    }
    open fun SetLan(value:String){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString("Lan",value).commit()
        Laninit()
        ViewInit()
    }
    open fun Laninit(){
        val profilePreferences =act.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        when(profilePreferences.getString("Lan",LOCALE_ENGLISH)){
            LOCALE_ENGLISH->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ENGLISH);}
            LOCALE_CHINESE->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_CHINESE);}
            LOCALE_TAIWAIN->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_TAIWAIN);}
            LOCALE_ITALIANO->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_ITALIANO);}
            LOCALE_DE->{LanguageUtil.updateLocale(activity, LanguageUtil.LOCALE_DE);}
        }
    }
    open fun Toast(a:String){
        act.Toast(a)
    }

    override fun dispatchKeyEvent(event: KeyEvent) {
    }
//===============================Abstract Function===============================
    /**
     * 載入rootview
     */
    abstract fun ViewInit()
}
