package ninh.luyen.github.data.remote

import androidx.paging.Pager
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.PhotosResponse
import ninh.luyen.github.data.dto.profile.ProfileModel

/**
 * Created by luyen_ninh on 01/06/2021
 */

internal interface RemoteDataSource {
    suspend fun requestGetProfile(name: String): Resource<ProfileModel>
    suspend fun requestGetFollowers(name: String): Resource<List<ProfileModel>>
}

internal interface UnsplashDataSource {
    fun getPhotos(category: String, firstPage:Int): Pager<Int, PhotoModel>
    suspend fun getPhoto(name: String): Resource<PhotoModel>
}
