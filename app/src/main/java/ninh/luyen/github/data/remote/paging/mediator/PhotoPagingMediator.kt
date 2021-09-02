package ninh.luyen.github.data.remote.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.squareup.moshi.Moshi
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.local.db.AppDatabase
import ninh.luyen.github.data.local.entites.RemoteKeys
import ninh.luyen.github.data.local.mapers.toPhotoEntity
import ninh.luyen.github.data.remote.UnsplashRemoteData
import ninh.luyen.github.utils.logE
import java.io.InvalidObjectException

/**
 * Created by luyen_ninh on 13/07/2021.
 */
@ExperimentalPagingApi
class PhotoPagingMediator(
    private val query: String,
    private val remote: UnsplashRemoteData,
    private val moshi: Moshi,
    private val database: AppDatabase
) : RemoteMediator<Int, PhotoModel>() {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1

    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoModel>
    ): MediatorResult {

        "load $loadType ,${state.config.pageSize}".logE()
        var pageKeyData: Any? = null
        try {
            pageKeyData = getKeyPageData(loadType, state)

        } catch (e: Exception) {
            e.message.logE()
            e.printStackTrace()
        }
        if (pageKeyData == null)
            return MediatorResult.Error(Throwable("DATA ERROR"))

        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                "MediatorResult.Success".logE()
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        "page :$page".logE()
        val option = HashMap<String, String>()
        option["query"] = query
        option["per_page"] = "200"
        option["w"] = "1080"
        option["h"] = "1980"
        option["orientation"] = "portrait"
        option["page"] = "$page"

        val photoDAO = database.photoDAO()
        val keyDAO = database.remoteKeyDAO()
        val response = remote.getPhotoMediator(option)

        val items: List<PhotoModel> = response.data?.results
            ?: return MediatorResult.Error(Throwable(""))

        val itemEntity = items.map { it.toPhotoEntity(moshi) }


        if (response is Resource.NetWorkError) {
            return MediatorResult.Error(Throwable("NO_INTERNET_CONNECTION"))
        }

        return if (response is Resource.Success) {
            val totalPage = response.data.total_pages
            response.data.results.size.logE()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    keyDAO.clearRemoteKeys()
                    photoDAO.deletePhotos()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (page == totalPage) null else page + 1
                val keys = itemEntity.map {
                    "repoId = ${it.id}, prevKey = $prevKey, nextKey = $nextKey".logE()
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                keyDAO.insertAll(keys)
                photoDAO.insertPhotos(*itemEntity.toTypedArray())
            }
            "$page == $totalPage".logE()
            MediatorResult.Success(page == totalPage)
        } else
            MediatorResult.Error(Throwable("DATA ERROR"))

    }

    /**
     * this returns the page key or the final end of list success result
     */
    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, PhotoModel>
    ): Any {
        loadType.logE()
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                "remotekey :$remoteKeys".logE()
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, PhotoModel>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { photoModel ->
                "getLastRemoteKey ${photoModel.id}".logE()
                database.remoteKeyDAO().remoteKeysPhotoId(photoModel.id)
            }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, PhotoModel>): RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { photoModel ->
                    "getFirstRemoteKey ${photoModel.id}".logE()
                database.remoteKeyDAO().remoteKeysPhotoId(photoModel.id)
            }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, PhotoModel>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeyDAO().remoteKeysPhotoId(repoId)
            }
        }
    }
}