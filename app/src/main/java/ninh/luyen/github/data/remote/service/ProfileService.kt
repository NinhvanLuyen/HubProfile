package ninh.luyen.github.data.remote.service

import ninh.luyen.github.data.dto.profile.ProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by luyen_ninh on 01/06/2021
 */

interface ProfileService {
    @GET("users/{name}")
    suspend fun fetchProfile(@Path("name") name:String): Response<ProfileModel>

    @GET("users/{name}/followers")
    suspend fun fetchFollowers(@Path("name") name:String): Response<List<ProfileModel>>
}
