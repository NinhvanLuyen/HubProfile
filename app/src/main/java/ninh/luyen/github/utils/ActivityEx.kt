package ninh.luyen.github.utils

import android.app.Activity
import android.transition.Fade
import android.transition.Transition


/**
 * Created by luyen_ninh on 17/08/2021.
 */
fun Activity.fadeTransition() {
    val fade: Transition = Fade()
    fade.excludeTarget(android.R.id.statusBarBackground, true)
    fade.excludeTarget(android.R.id.navigationBarBackground, true)

    window.enterTransition = fade
    window.exitTransition = fade
}