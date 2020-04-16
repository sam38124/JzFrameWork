package com.orange.jzframework.page

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import com.orange.jzchi.jzframework.Animator
import com.orange.jzchi.jzframework.callback.SetupDialog
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzchi.jzframework.Theme.Theme
import com.orange.jzchi.jzframework.callback.DownloadCallback
import com.orange.jzchi.jzframework.callback.permission_C
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.sec.view.*
import java.io.File

class Page_Sec : JzFragement(R.layout.sec) {

    override fun viewInit() {
        rootview.sampletext2.setOnClickListener {
            /*
            使用 ShowDaiLog 的方法顯示客製化Dialog
            cancelable 決定Dialog是否可以被點擊消失
            swipe 決定Dialog背景是否透明反之為不透明
            R.layout.sampledialog 換成你的Dialog layout
            */
            JzActivity.getControlInstance().showDiaLog( true, false, object : SetupDialog(R.layout.sampledialog) {
                override fun keyevent(event: KeyEvent): Boolean {
                    //按鈕事件監聽
                    // return true後會繼續執行父類別的dispathKeyevent方法，反之攔截按鈕事件
                    return true
                }

                override fun setup(rootview: Dialog) {
                    //Dialog的載入設定
                    rootview.findViewById<Button>(R.id.button).setOnClickListener {
                        rootview.dismiss()
                    }
                }

                override fun dismess() {
                    //關閉事件的監聽
                }
            },"")
        }
        rootview.button2.setOnClickListener {
            JzActivity.getControlInstance().goBack()
        }
        rootview.button3.setOnClickListener {
            JzActivity.getControlInstance().showCustomDaiLog(true,
                Theme.downToUP,object :SetupDialog(R.layout.third_page){
                override fun setup(rootview: Dialog) {

                }

                override fun dismess() {
                }

                override fun keyevent(event: KeyEvent): Boolean {
                    return true
                }
            },"third_page")
//            JzActivity.getControlInstance().changePage(Page_Third(), "Page_Third", true,Animator.verticalTranslation)
        }

        rootview.button6.setOnClickListener {
            JzActivity.getControlInstance().permissionRequest(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), object :
                    permission_C {
                    var ok = ArrayList<String>()
                    override fun requestSuccess(a: String) {
                        ok.add(a)
                        if (ok.size == 2) {
                            Thread {
                                JzActivity.getControlInstance().apkDownload(
                                    "https://bento2.orange-electronic.com/Orange%20Cloud/Beta/Drive/OG/APP%20Software/Beta.apk",
                                    object :
                                        DownloadCallback {
                                        override fun result(a: Boolean) {
                                            if (a) {
                                                JzActivity.getControlInstance().openAPK()
                                            }
                                            Log.e("download", "$a")
                                        }

                                        override fun progress(a: Int) {
                                            Log.e("download", "$a")
                                        }

                                    })
                            }.start()
                        }
                    }

                    override fun requestFalse(a: String) {

                    }
                }, 10
            )
        }
    }

}