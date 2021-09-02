package ninh.luyen.github.data.remote

import androidx.paging.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.PhotosResponse
import ninh.luyen.github.data.remote.commons.BaseRemoteData
import ninh.luyen.github.data.remote.commons.UnSplashServiceGenerator
import ninh.luyen.github.data.remote.paging.mediator.PhotoPagingMediator
import ninh.luyen.github.data.remote.paging.sources.PhotoPagingSource
import ninh.luyen.github.data.remote.service.UnsplashService
import ninh.luyen.github.utils.NetworkConnectivity
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by luyen_ninh on 12/07/2021.
 */
class UnsplashRemoteData @Inject
constructor(
    serviceGenerator: UnSplashServiceGenerator,
    private val networkConnectivity: NetworkConnectivity,
) : BaseRemoteData(networkConnectivity),
    UnsplashDataSource {

    private val unsplashService = serviceGenerator.createService(UnsplashService::class.java)

    override fun getPhotos(category: String, firstPage: Int): Pager<Int, PhotoModel> {

        return Pager(config = PagingConfig(pageSize = 20, maxSize = 60),
            pagingSourceFactory = {
                PhotoPagingSource(category, unsplashService, networkConnectivity, firstPage)
            })
    }

    override suspend fun getPhotoMediator(option: Map<String, String>): Resource<PhotosResponse> {
        val url = "https://api.unsplash.com/search/photos?"
        if (!networkConnectivity.isConnected()) {
            return Resource.NetWorkError()
        }
        return when (val res = processCall { unsplashService.getPhotos(url, option) }) {
            is PhotosResponse -> Resource.Success(res)
            else -> Resource.DataError(res as Int)

        }
    }

    override suspend fun getPhoto(name: String): Resource<PhotoModel> {
        return when (val res = processCall { unsplashService.getPhotoDetails() }) {
            is PhotoModel -> Resource.Success(res)
            else -> Resource.DataError(res as Int)
        }
    }

}