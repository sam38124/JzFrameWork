package com.orange.jzchi.jzframework.callback

import android.content.Context
import com.orange.jzchi.jzframework.JzActivity

open class RootShare(val jzShare_Act:JzActivity)  {
     fun SetPro(key:String,value:Boolean){
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putBoolean(key,value).commit()
    }
     fun SetPro(key:String,value:String){
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putString(key,value).commit()
    }
     fun SetPro(key:String,value:Int){
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        profilePreferences.edit().putInt(key,value).commit()
    }
     fun GetPro(key:String,value:String):String{
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getString(key,value)
    }
     fun GetPro(key:String,value:Boolean):Boolean{
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getBoolean(key,value)
    }
     fun GetPro(key:String,value:Int):Int{
        val profilePreferences =jzShare_Act!!.getSharedPreferences("Setting", Context.MODE_PRIVATE)
        return profilePreferences.getInt(key,value)
    }

}