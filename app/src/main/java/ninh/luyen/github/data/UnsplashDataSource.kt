package ninh.luyen.github.data

import androidx.paging.Pager
import kotlinx.coroutines.flow.Flow
import ninh.luyen.github.data.dto.photos.PhotoModel

/**
 * Created by luyen_ninh on 13/07/2021.
 */
interface UnsplashDataSource {

    suspend fun getPhoto(): Flow<Resource<PhotoModel>>

    fun getPhotos(query:String): Pager<Int, PhotoModel>

}