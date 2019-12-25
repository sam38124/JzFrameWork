package com.orange.jzchi.jzframework.CallBack

import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.os.Handler
import androidx.fragment.app.Fragment
import com.orange.jzchi.R
import com.orange.jzchi.jzframework.Server.BleServiceControl
import com.orange.jzchi.jzframework.Server.ScanDevice

 class Ble_Helper(val caller:Ble_CallBack,val context: Context) {
    var handler= Handler()
    var bleServiceControl=BleServiceControl()
    var scan = ScanDevice(this,context)
    fun Connect(a: String,time:Int) {
        caller.Connecting()
        bleServiceControl.bleCallbackC=this
        bleServiceControl.connect(a)
        Thread {
            var fal = 0
            while (true) {
                if (bleServiceControl.isconnect || fal == time) {
                    break
                }
                Thread.sleep(1000)
                fal++
            }
            handler.post {
                if (bleServiceControl.isconnect) {
                    caller.ConnectSuccess()
                }else{
                    caller.ConnectFalse()
                }
            }
            StopScan()
        }.start()
    }

    fun StartScan() {
        scan.setmBluetoothAdapter()
    }
    fun StopScan(){
        scan.scanLeDevice(false)
    }
    interface Ble_CallBack{
        fun Connecting()
        fun ConnectFalse()
        fun ConnectSuccess()
        fun RX(a:String)
        fun TX(b:String)
        fun ScanBack(device:BluetoothDevice)
        fun RequestPermission(permission:ArrayList<String>)
    }
}


