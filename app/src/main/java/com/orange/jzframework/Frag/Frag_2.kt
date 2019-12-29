package com.orange.jzframework.Frag

import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.thirdpage_frag.view.*
import kotlinx.android.synthetic.main.thirdpage_frag2.view.*

class Frag_2 : JzFragement(R.layout.thirdpage_frag2) {
    override fun viewInit() {
        rootview.button5.setOnClickListener {
                JzActivity.getControlInstance().goBack()
        }
    }

}