package com.orange.jzframework.Frag

import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.thirdpage_frag.view.*

class Frag_1 : JzFragement(R.layout.thirdpage_frag) {
    override fun viewInit() {
        rootview.button4.setOnClickListener {
            JzActivity.getControlInstance().changeFrag(Frag_2(), R.id.frageplace,"Frag_2", true)
        }
    }

}