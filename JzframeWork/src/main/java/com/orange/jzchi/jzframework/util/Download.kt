package com.orange.jzchi.jzframework.util

import android.util.Log
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.callback.DownloadCallback
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Download{
    companion object{
        fun apkDownload(url:String,caller: DownloadCallback) {
            try {
//            URL(url).openConnection().contentLength
                val file=File("/sdcard/update/")
                if(!file.exists()){file.mkdirs()}
                File("/sdcard/update/beta.apk").createNewFile()
                val stream = URL(url).openConnection().getInputStream()
                val fos = FileOutputStream("/sdcard/update/beta.apk")
                val bufferSize = 8192
                val buf = ByteArray(bufferSize)
                while (true) {
                    val read = stream.read(buf)
                    if (read == -1) {
                        break
                    }
                    fos.write(buf, 0, read)
                }
                stream.close()
                fos.close()
                val f = File("beta.apk")
                if (f.exists() && f.isFile()) {
                    Log.d("path", "" + f.length())
                } else {
                    Log.d("path", "file doesn't exist or is not a file")
                }
                caller.result(true)
            } catch (e: Exception) {
                e.printStackTrace()
                caller.result(false)}
        }
    }
}
