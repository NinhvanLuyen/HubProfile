package ninh.luyen.github.utils

import android.util.Log
import ninh.luyen.github.BuildConfig


/**
 * Created by luyen_ninh on 2/24/21.
 */
fun Any.logE() {
    if (BuildConfig.DEBUG) {
        if (this is String)
            Log.e("WTF", this)
        else
            Log.e("WTF", this.toString())

    }
}

fun Any.logD() {
    if (BuildConfig.DEBUG) {
        if (this is String)
            Log.d("WTF", this)
        else
            Log.d("WTF", this.toString())

    }

}

fun Any.logW() {
    if (BuildConfig.DEBUG) {
        if (this is String)
            Log.d("WTF", this)
        else
            Log.d("WTF", this.toString())

    }
}