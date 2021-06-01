package ninh.luyen.github.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.data.remote.RemoteData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by luyen_ninh on 01/06/2021
 */

class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) :
    DataRepositorySource {


    override suspend fun requestGetProfile(): Flow<Resource<ProfileModel>> {
        return flow {
            emit(remoteRepository.requestGetProfile())
        }.flowOn(ioDispatcher)
    }

    override suspend fun requestGetFollowers(): Flow<Resource<List<ProfileModel>>> {
        return flow {
            emit(remoteRepository.requestGetFollowers())
        }.flowOn(ioDispatcher)
    }
}
