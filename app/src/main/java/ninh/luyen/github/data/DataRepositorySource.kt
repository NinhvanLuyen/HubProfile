package ninh.luyen.github.data

import kotlinx.coroutines.flow.Flow
import ninh.luyen.github.data.dto.profile.ProfileModel

/**
 * Created by luyen_ninh on 01/06/2021
 */

interface DataRepositorySource {
    suspend fun requestGetProfile(name: String): Flow<Resource<ProfileModel>>
    suspend fun requestGetFollowers(name: String): Flow<Resource<List<ProfileModel>>>

}
