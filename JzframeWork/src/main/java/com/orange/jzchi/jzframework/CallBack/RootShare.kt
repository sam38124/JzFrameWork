package com.orange.jzchi.jzframework.CallBack

import android.app.Activity
import android.content.Context
import com.orange.jzchi.jzframework.RootActivity

open class RootShare(val RootShare_Act:RootActivity)  {
     fun SetPro(key:String,value:Boolean){
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putBoolean(key,value).commit()
    }
     fun SetPro(key:String,value:String){
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString(key,value).commit()
    }
     fun SetPro(key:String,value:Int){
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putInt(key,value).commit()
    }
     fun GetPro(key:String,value:String):String{
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getString(key,value)
    }
     fun GetPro(key:String,value:Boolean):Boolean{
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getBoolean(key,value)
    }
     fun GetPro(key:String,value:Int):Int{
        val profilePreferences =RootShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getInt(key,value)
    }

}