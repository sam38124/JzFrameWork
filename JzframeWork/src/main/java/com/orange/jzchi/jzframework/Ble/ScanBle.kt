package com.orange.jzchi.jzframework.Ble

import android.app.Dialog
import android.bluetooth.BluetoothDevice
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log

import com.orange.jzchi.jzframework.CallBack.Ble_Helper
import com.orange.jzchi.jzframework.CallBack.Dailog_SetUp_C
import com.orange.jzchi.jzframework.RootActivity
import com.orange.jzchi.jzframework.Server.ScanDevice
import kotlinx.android.synthetic.main.activity_scan_ble.*

import java.util.ArrayList


//class ScanBle(val caller:Ble_Helper) : Dailog_SetUp_C() {
//
//    internal var ble = ArrayList<BluetoothDevice>()
//    internal var selectBle = SelectBle(ble, this)
//    var scan = ScanDevice()
//    lateinit var act: RootActivity
//
//    override fun SetUP(root: Dialog, act: RootActivity) {
//        this.act=act
//        root.re.layoutManager = LinearLayoutManager(act)
//        root.re.adapter = selectBle
//        scan.scanBle=this
//        scan.setmBluetoothAdapter(act)
//        root.close.setOnClickListener {
//            act.DaiLogDismiss()
//        }
//    }
//
//    override fun Dismiss() {
//        scan.scanLeDevice(false)
//        super.Dismiss()
//    }
//
//    fun DataRefresh(a: BluetoothDevice) {
//        try {
//            if (!ble.contains(a) && a.name != null) {
//                ble.add(a)
//                selectBle.notifyDataSetChanged()
//            }
//        } catch (e: Exception) {
//            Log.w("error", e.message)
//        }
//    }
//
//
//}
