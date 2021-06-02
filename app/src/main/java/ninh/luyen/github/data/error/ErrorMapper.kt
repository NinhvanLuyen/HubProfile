package ninh.luyen.github.data.error

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ninh.luyen.github.R
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(RATE_LIMIT, getErrorString(R.string.server_error_rate_limit)),
        ).withDefault { getErrorString(R.string.network_error) }
}
