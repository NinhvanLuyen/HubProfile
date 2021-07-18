package ninh.luyen.github.data.remote

import androidx.paging.*
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.remote.commons.BaseRemoteData
import ninh.luyen.github.data.remote.commons.UnSplashServiceGenerator
import ninh.luyen.github.data.remote.service.UnsplashService
import ninh.luyen.github.utils.NetworkConnectivity
import javax.inject.Inject

/**
 * Created by luyen_ninh on 12/07/2021.
 */
class UnsplashRemoteData @Inject
constructor(
    serviceGenerator: UnSplashServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : BaseRemoteData(networkConnectivity),
    UnsplashDataSource {

    private val unsplashService = serviceGenerator.createService(UnsplashService::class.java)

    override fun getPhotos(category: String, nextPage:Int): Pager<Int, PhotoModel> {

         return Pager(config = PagingConfig(pageSize = 20,maxSize = 60),
             pagingSourceFactory = {
                 PhotoPagingSource(category, unsplashService, networkConnectivity)
             })
//        return when (val res = processCall {
//            val option = HashMap<String, String>()
//            option["query"] = category
//            option["per_page"] = "20"
//            option["w"] = "1080"
//            option["h"] = "1980"
//            option["orientation"] = "portrait"
//            option["page"] = "$nextPage"
//            unsplashService.getPhotos("https://api.unsplash.com/search/photos?",option)
//        }) {
//            is PhotosResponse->
//                Resource.Success(res)
//            else ->
//                Resource.DataError(res as Int)
//        }
    }

    override suspend fun getPhoto(name: String): Resource<PhotoModel> {
        return when (val res = processCall { unsplashService.getPhotoDetails() }) {
            is PhotoModel -> Resource.Success(res)
            else -> Resource.DataError(res as Int)
        }
    }

}