package ninh.luyen.github.data.remote.service

import ninh.luyen.github.data.dto.photos.PhotosResponse
import ninh.luyen.github.data.dto.photos.PhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Created by luyen_ninh on 08/07/2021.
 */
interface UnsplashService {
    @GET
    suspend fun getPhotoDetails(): Response<PhotoModel>

    @GET
    suspend fun getPhotos(
        @Url url: String,
        @QueryMap option: Map<String, String>
    ): Response<PhotosResponse>
}