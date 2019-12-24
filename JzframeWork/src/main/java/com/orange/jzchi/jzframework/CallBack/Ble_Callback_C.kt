package com.orange.jzchi.jzframework.CallBack

import androidx.fragment.app.Fragment
import com.orange.jzchi.R
import com.orange.jzchi.jzframework.Ble.ScanBle
import com.orange.jzchi.jzframework.RootActivity
import com.orange.jzchi.jzframework.Server.BleServiceControl

interface Ble_Callback_C {
    var RXDATA: String
    var id: Int
    var tag: String
    var ConnectDelay: Int
    var Translation: Fragment
    var bleServiceControl: BleServiceControl
    fun bleact(): RootActivity
    abstract fun RX(a: String)
    abstract fun TX(a: String)
    fun ConnectSituation(boolean: Boolean) {}

    fun connect(a: String) {
        bleact().ShowDaiLog(R.layout.dataloading, false, false, Dailog_SetUp_C())
        bleServiceControl.bleCallbackC=this
        bleServiceControl.connect(a)
        Thread {
            var fal = 0
            while (true) {
                if (bleServiceControl.isconnect || fal == ConnectDelay) {
                    break
                }
                Thread.sleep(1000)
                fal++
            }
            bleact().handler.post {
                bleact().DaiLogDismiss()
                if (bleServiceControl.isconnect) {
                    val transaction = bleact().supportFragmentManager.beginTransaction()
                    transaction.replace(id, Translation, tag)
                            .addToBackStack(null)
                            .commit()
                }
            }
        }.start()
    }

    fun GoScanner(Translation: Fragment, DelayTime: Int, id: Int, tag: String) {
        ConnectDelay = DelayTime
        this.Translation = Translation
        this.id = id
        this.tag = tag;
        if (bleServiceControl.isconnect) {
            val transaction = bleact().supportFragmentManager.beginTransaction()
            transaction.replace(id, Translation, tag)
                    .addToBackStack(null)
                    .commit()
        } else {
            bleact().ShowDaiLog(R.layout.activity_scan_ble, false, false, ScanBle(this))
        }
    }
}


