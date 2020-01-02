package com.orange.jzchi.jzframework.callback

interface DownloadCallback {
    fun result(a:Boolean)
    fun progress(a:Int)
}