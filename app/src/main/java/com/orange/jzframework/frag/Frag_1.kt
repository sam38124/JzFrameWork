package com.orange.jzframework.frag

import com.orange.jzchi.jzframework.Animator
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.R
import com.orange.jzframework.page.Page_Sec
import kotlinx.android.synthetic.main.thirdpage_frag.view.*

class Frag_1 : JzFragement(R.layout.thirdpage_frag) {
    override fun viewInit() {
        rootview.button4.setOnClickListener {
            JzActivity.getControlInstance().changePage(Page_Sec(), "$fragId", true, Animator.translation)
        }
    }

}