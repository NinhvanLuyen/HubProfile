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

    override suspend fun requestGetProfile(name: String): Resource<ProfileModel> {
        val recipesService = serviceGenerator.createService(ProfileService::class.java)
        return when (val response = processCall(name, recipesService::fetchProfile)) {
            is ProfileModel -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    override suspend fun requestGetFollowers(name: String): Resource<List<ProfileModel>> {
        val profileService = serviceGenerator.createService(ProfileService::class.java)

        return when (val response = processCall(name, profileService::fetchFollowers)) {
            is List<*> -> {
                Resource.Success(data = response as List<ProfileModel>)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(name: String, responseCall: suspend (name:String) -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(name)
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
