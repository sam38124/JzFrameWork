package com.orange.jzchi.jzframework

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RootAdapter(val layout:Int) : RecyclerView.Adapter<RootAdapter.ViewHolder>() {
     var handler= Handler()
     lateinit var  act:RootActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        Log.e("contex",""+(parent.context))
        if(parent.context is RootActivity){
            act=parent.context as RootActivity
        }else if(parent.context is android.view.ContextThemeWrapper){
            act=(parent.context as android.view.ContextThemeWrapper).baseContext as RootActivity
        }
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = SizeInit()

        inner class ViewHolder (val mView: View) : RecyclerView.ViewHolder(mView) {
            var obj:Any?=null
             init {
                 ViewInit(mView,this)
             }
             override fun toString(): String {
                 return super.toString() + " ''"
             }
         }

     abstract fun SizeInit():Int;
     abstract override fun onBindViewHolder(holder: ViewHolder, position: Int) ;
     open fun ViewInit(mView: View,holder: ViewHolder){};

}