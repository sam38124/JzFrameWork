package com.orange.jzchi.jzframework

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class JzAdapter(val layout: Int) : RecyclerView.Adapter<JzAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        Log.e("contex", "" + (parent.context))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = sizeInit()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var obj: Any? = null

        init {
            viewInit(mView, this)
        }

        override fun toString(): String {
            return super.toString() + " ''"
        }
    }

    abstract fun sizeInit(): Int
    abstract override fun onBindViewHolder(holder: ViewHolder, position: Int);
    open fun viewInit(mView: View, holder: ViewHolder) {};

}