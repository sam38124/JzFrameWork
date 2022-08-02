package com.orange.jzchi.jzframework

import com.orange.jzchi.R

object  Animator {
    var rotation= arrayOf(
        R.animator.enter_animtor,
        R.animator.exit_animtor,
        R.animator.pop_enter_animtor,
        R.animator.pop_exit_animtor)

    var translation= arrayOf(R.anim.slide_right_in,
        R.anim.slide_left_out,
        R.anim.slide_left_in,
        R.anim.slide_right_out)

    var verticalTranslation= arrayOf(R.anim.top_in,
        R.anim.top_out,
        R.anim.down_in,
        R.anim.down_out)
}