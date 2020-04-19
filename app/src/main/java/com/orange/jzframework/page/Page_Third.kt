package com.orange.jzframework.page

import com.orange.jzchi.jzframework.Animator
import com.orange.jzchi.jzframework.JzActivity
import com.orange.jzchi.jzframework.JzFragement
import com.orange.jzframework.frag.Frag_1
import com.orange.jzframework.R
import kotlinx.android.synthetic.main.third_page.view.*

class Page_Third : JzFragement(R.layout.third_page) {
    val frag=Frag_1()

    override fun viewInit() {
        //頁面中的fragment切換
        refresh=true
        rootview.imageView.setOnClickListener {
            JzActivity.getControlInstance().goBack()
        }
        JzActivity.getControlInstance().changeFrag(frag, R.id.frageplace, "Frag_1", false)
        rootview.textView4.setOnClickListener {
            JzActivity.getControlInstance().changePage(Page_Third(), "Page_Thirsd", true, Animator.verticalTranslation)

        }
    }

    override fun onResume() {
        super.onResume()
    }
}