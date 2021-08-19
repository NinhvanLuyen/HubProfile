package ninh.luyen.github.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by luyen_ninh on 18/08/2021.
 */
fun SearchView.getQueryTextChangeAsFlow(): StateFlow<String> {
    val flowSearch = MutableStateFlow("")
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(content: String?): Boolean {
            val mgr: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(windowToken, 0)
            return true
        }

        override fun onQueryTextChange(content: String?): Boolean {
            flowSearch.value = content ?: ""
            return true
        }

    })
    return flowSearch

}