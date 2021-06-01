package ninh.luyen.github.data.remote.service

import ninh.luyen.github.data.dto.profile.ProfileModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by luyen_ninh on 01/06/2021
 */

interface ProfileService {
    @GET("users/ninhvanluyen")
    suspend fun fetchProfile(): Response<ProfileModel>

    @GET("users/ninhvanluyen/followers")
    suspend fun fetchFollowers(): Response<List<ProfileModel>>
}
