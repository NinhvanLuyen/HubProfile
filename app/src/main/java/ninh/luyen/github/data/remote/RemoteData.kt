package ninh.luyen.github.data.remote

import ninh.luyen.github.utils.NetworkConnectivity
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.data.error.NETWORK_ERROR
import ninh.luyen.github.data.error.NO_INTERNET_CONNECTION
import ninh.luyen.github.data.remote.service.ProfileService
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


/**
 * Created by luyen_ninh on 01/06/2021
 */

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) :
    RemoteDataSource {

    override suspend fun requestGetProfile(): Resource<ProfileModel> {
        val recipesService = serviceGenerator.createService(ProfileService::class.java)
        return when (val response = processCall(recipesService::fetchProfile)) {
            is List<*> -> {
                Resource.Success(data = response as ProfileModel)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestGetFollowers(): Resource<List<ProfileModel>> {
        val recipesService = serviceGenerator.createService(ProfileService::class.java)
        return when (val response = processCall(recipesService::fetchFollowers)) {
            is List<*> -> {
                Resource.Success(data = response as ArrayList<ProfileModel>)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
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
