package com.orange.jzchi.jzframework.Util

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class JzScrollLinster: RecyclerView.OnScrollListener(){
    var scrolly = 0F
    var scrollx = 0F
    var FocusPosition=0
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val b = recyclerView.canScrollVertically(1)
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE //滾動停止
            -> {
                Log.e("scroll","滾動停止")
                Scroll_Stop()
            }
            RecyclerView.SCROLL_STATE_DRAGGING //手指拖動
            -> {
                Scroll_DRAGGING()
                Log.e("scroll","手指拖動")
            }
            RecyclerView.SCROLL_STATE_SETTLING //慣性滾動
            -> {
                Scroll_SETTLING()
                Log.e("scroll","慣性滾動")
            }
        }

        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager =  recyclerView.layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition().toFloat();
            val lastPosition = layoutManager.findLastVisibleItemPosition().toFloat();
            Log.e("scrollposition","first:$firstPosition")
            Log.e("scrollposition","last:$lastPosition")
            FocusPosition=Math.round(firstPosition+((lastPosition-firstPosition)/2))
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        scrolly -= dy
        scrollx -= dx
        Translation(dx, dy)
    }
    abstract fun Translation(dx: Int, dy: Int)
    open fun Scroll_Stop(){}
    open fun Scroll_DRAGGING(){}
    open fun Scroll_SETTLING(){}
}
