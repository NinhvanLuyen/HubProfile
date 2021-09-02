package ninh.luyen.github.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.local.dao.PhotoDAO
import ninh.luyen.github.data.local.db.AppDatabase
import ninh.luyen.github.data.local.mapers.toPhotoModel
import ninh.luyen.github.data.remote.UnsplashRemoteData
import ninh.luyen.github.data.remote.paging.mediator.PhotoPagingMediator
import ninh.luyen.github.utils.logE
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by luyen_ninh on 13/07/2021.
 */
data class UnsplashRepository @Inject constructor(
    private val unplashRemote: UnsplashRemoteData,
    private val ioDispatcher: CoroutineContext,
    private val photoLocalStorage: AppDatabase,
    private val moshi: Moshi

) :
    UnsplashDataSource {

    private val photoDAO: PhotoDAO = photoLocalStorage.photoDAO()

    override suspend fun getPhoto(): Flow<Resource<PhotoModel>> {
        return flow {
            emit(unplashRemote.getPhoto("hello"))
        }.flowOn(ioDispatcher)
    }

    override fun getPhotos(query: String): Pager<Int, PhotoModel> {

        return unplashRemote.getPhotos(query, 1)
    }

    @ExperimentalPagingApi
    override fun getPhotosMediator(query: String): Pager<Int, PhotoModel> {
        return Pager(
            config = PagingConfig(pageSize = 30, maxSize = 90),
            remoteMediator = PhotoPagingMediator(
                query = query, unplashRemote,
                moshi = moshi,
                database = photoLocalStorage
            ),

            pagingSourceFactory = {
                photoDAO.getPhotosPaging().map {
                    it.toPhotoModel(moshi)
                }.asPagingSourceFactory().invoke()

            }
        )
    }
}
