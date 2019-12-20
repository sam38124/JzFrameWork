package com.orange.jzchi.jzframework.Server

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.orange.jzchi.R

import com.orange.jzchi.jzframework.Ble.ScanBle
import com.orange.jzchi.jzframework.CallBack.Permission_C
import com.orange.jzchi.jzframework.RootActivity

import java.util.ArrayList

class ScanDevice {
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var activity: RootActivity? = null
    var scanBle: ScanBle? = null
    private val mLeDevices = ArrayList<BluetoothDevice>()
    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        if (!mLeDevices.contains(device)) {
            scanBle!!.DataRefresh(device)
        }
        val stringBuilder = StringBuilder()
        for (a in scanRecord) {
            stringBuilder.append(String.format("%02X", a))
        }
        Log.d("scanrecord", stringBuilder.toString())
        try {
            Log.d("name", device.name)
        } catch (e: Exception) {
            mLeDevices.add(device)
            Log.d("name", "UNROWN")
        }
    }

    fun setmBluetoothAdapter(setting: RootActivity) {
        this.activity = setting
        initPermission(setting)
        val bluetoothManager = setting.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter
        if (mBluetoothAdapter == null) {
            Toast.makeText(setting, "notsupport", Toast.LENGTH_SHORT).show()
        }
    }

    //-----------------------method1檢查權限------------------------------------------
    fun initPermission(context: RootActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.GetPermission(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION), object :Permission_C{
                var agrant=ArrayList<String>();
                override fun RequestSuccess(a: String) {
                    agrant.add(a)
                    if(agrant.size==2){RequestPermission()}
                }

                override fun RequestFalse(a: String) {
                    context.Toast(R.string.app_permission_requre)
                }
            },20);
        } else {
            RequestPermission()
        }
    }

    fun RequestPermission() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!isLocServiceEnable(activity!!)) {
            Toast.makeText(activity, "Please enable GPS", Toast.LENGTH_SHORT).show()
        }
        val originalBluetooth = mBluetoothAdapter != null && mBluetoothAdapter!!.isEnabled
        if (originalBluetooth) {
            scanLeDevice(true)
            mBluetoothAdapter!!.startDiscovery()
        } else if (originalBluetooth == false) {
            scanLeDevice(true)
            mBluetoothAdapter!!.enable()
        }
    }

    //----------method2開始掃描
    fun scanLeDevice(enable: Boolean) {
        if (enable) {
            mLeDevices.clear()
            if (mBluetoothAdapter == null) {
                Log.w("ss", "是null")
            }
            mBluetoothAdapter!!.startLeScan(mLeScanCallback)
        } else {
            mBluetoothAdapter!!.stopLeScan(mLeScanCallback)
        }
    }

    companion object {
        fun isLocServiceEnable(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            return gps || network
        }
    }

}
