package ninh.luyen.github.data

import androidx.paging.Pager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.PhotosResponse
import ninh.luyen.github.data.remote.UnsplashRemoteData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by luyen_ninh on 13/07/2021.
 */
data class UnsplashRepository @Inject constructor(
    private val remoteRepository: UnsplashRemoteData,
    private val ioDispatcher: CoroutineContext
) :
    UnsplashDataSource {


    override suspend fun getPhoto(): Flow<Resource<PhotoModel>> {
        return flow {
            emit(remoteRepository.getPhoto("hello"))
        }.flowOn(ioDispatcher)
    }

    override fun getPhotos(query:String): Pager<Int, PhotoModel> {
        return remoteRepository.getPhotos(query,1)
    }
}
