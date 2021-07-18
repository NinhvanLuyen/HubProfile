package ninh.luyen.github.data.remote.commons

import ninh.luyen.github.data.error.NETWORK_ERROR
import ninh.luyen.github.data.error.NO_INTERNET_CONNECTION
import ninh.luyen.github.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException

/**
 * Created by luyen_ninh on 12/07/2021.
 */
open class BaseRemoteData(private val networkConnectivity: NetworkConnectivity) {

    protected suspend fun processCall(
        responseCall: suspend () -> Response<*>
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }


}
