package ninh.luyen.github.ui.screen.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ninh.luyen.github.data.DataRepositorySource
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by luyen_ninh on 02/06/2021.
 */
@HiltViewModel
class FollowersViewModel @Inject
constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {


    private val followers: MutableLiveData<Resource<List<ProfileModel>>> = MutableLiveData()
    val followersLive: LiveData<Resource<List<ProfileModel>>>
        get() = followers

    private val onDetailProfile: MutableLiveData<ProfileModel> = MutableLiveData()
    val openProfileDetailLive: LiveData<ProfileModel>
        get() = onDetailProfile



    private val loadingBind: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLive: LiveData<Boolean>
        get() = loadingBind

    private val toastMessage: MutableLiveData<String> = MutableLiveData()

    val toastLive: LiveData<String>
        get() = toastMessage

    fun getErrorCode(code: Int){
        val error = errorManager.getError(code)
        toastMessage.value = error.description
    }

    fun openProfileOfFollower(profile: ProfileModel) {
        onDetailProfile.value = profile
    }

    fun getFollowers(name: String) {
        viewModelScope.launch {
            loadingBind.value = true
            dataRepository.requestGetFollowers(name).collect {
                loadingBind.value = false
                followers.value = it
            }
        }
    }


}