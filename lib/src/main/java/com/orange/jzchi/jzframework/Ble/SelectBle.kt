package com.orange.jzchi.jzframework.Ble;


import android.bluetooth.BluetoothDevice
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import android.widget.TextView
import com.orange.jzchi.R
import java.util.ArrayList


class SelectBle(private val a:ArrayList<BluetoothDevice>, private val scanner: ScanBle)
    : RecyclerView.Adapter<SelectBle.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.selectble, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(a[position].name==null){holder.device.text="Unknown Device"}else{holder.device.text=a[position].name}

        holder.address.text=a[position].address
        holder.mView.setOnClickListener(View.OnClickListener {
            scanner.caller.connect(a[position].address)
            scanner.scan.scanLeDevice(false)
        })
    }

    override fun getItemCount(): Int = a.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val device: TextView = mView.findViewById(R.id.textView)
        val address: TextView = mView.findViewById(R.id.textView2)
        override fun toString(): String {
            return super.toString() + " ''"
        }
    }
}