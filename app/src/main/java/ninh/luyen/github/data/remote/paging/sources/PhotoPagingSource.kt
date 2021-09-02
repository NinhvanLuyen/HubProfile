package ninh.luyen.github.data.remote.paging.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.error.NETWORK_ERROR
import ninh.luyen.github.data.error.NO_INTERNET_CONNECTION
import ninh.luyen.github.data.remote.service.UnsplashService
import ninh.luyen.github.utils.NetworkConnectivity
import java.io.IOException

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class PhotoPagingSource(
    private val query: String,
    private val photoService: UnsplashService,
    private val networkConnectivity: NetworkConnectivity,
    private val page:Int
) : PagingSource<Int, PhotoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoModel> {
        val currentPage = params.key ?: page
        val option = HashMap<String, String>()
        option["query"] = query
        option["per_page"] = "200"
        option["w"] = "1080"
        option["h"] = "1980"
        option["orientation"] = "portrait"
        option["page"] = "$currentPage"

        if (!networkConnectivity.isConnected()) {
            return LoadResult.Error(Throwable("NO_INTERNET_CONNECTION"))
        }
        return try {
            val response = photoService.getPhotos("https://api.unsplash.com/search/photos?", option)
            val responseCode = response.code()
            if (response.isSuccessful) {
                val totalPage = response.body()?.total_pages
                return LoadResult.Page(
                    data = response.body()?.results ?: emptyList(),
                    nextKey = if(currentPage == totalPage)
                        null
                    else
                        currentPage + 1,
                    prevKey = null
                )
            } else {
                LoadResult.Error(Throwable("$responseCode"))
            }
        } catch (e: IOException) {
            LoadResult.Error(Throwable("NETWORK_ERROR"))
        }

    }

    override fun getRefreshKey(state: PagingState<Int, PhotoModel>): Int? {
        return 0
    }


}