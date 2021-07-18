package ninh.luyen.github.data.remote

import ninh.luyen.github.utils.NetworkConnectivity
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.data.remote.commons.BaseRemoteData
import ninh.luyen.github.data.remote.commons.ServiceGenerator
import ninh.luyen.github.data.remote.service.ProfileService
import javax.inject.Inject


/**
 * Created by luyen_ninh on 01/06/2021
 */

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator,
            networkConnectivity: NetworkConnectivity) : BaseRemoteData(networkConnectivity),
    RemoteDataSource {

    override suspend fun requestGetProfile(name: String): Resource<ProfileModel> {
        val recipesService = serviceGenerator.createService(ProfileService::class.java)
        return when (val response = processCall { recipesService.fetchProfile(name) }) {
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

        return when (val response = processCall {
            profileService.fetchFollowers(name)
        }) {
            is List<*> -> {
                Resource.Success(data = response as List<ProfileModel>)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }
}
