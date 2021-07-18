package ninh.luyen.github.utils

import android.app.Service
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ninh.luyen.github.R


fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}

fun View.showOrGoneByCondition(needShow: Boolean) {
    this.visibility = if (needShow) View.VISIBLE else View.GONE
}


fun View.showToast(message: String, timeLength: Int) {
    Toast.makeText(this.context, message, timeLength).show()

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun ImageView.loadImage(url: String) =
    Picasso.get().load(url).placeholder(R.drawable.ic_avatar_holder)
        .error(R.drawable.ic_avatar_error).into(this)

fun ImageView.loadImage(url: String,callback:Callback) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_avatar_holder)
        .error(R.drawable.ic_avatar_error)
        .into(this, callback)
}

fun AppCompatTextView.setTextFutureExt(text: String) =
    setTextFuture(
        PrecomputedTextCompat.getTextFuture(
            text,
            TextViewCompat.getTextMetricsParams(this),
            null
        )
    )

fun AppCompatEditText.setTextFutureExt(text: String) =
    setText(
        PrecomputedTextCompat.create(text, TextViewCompat.getTextMetricsParams(this))
    )
