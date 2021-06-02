package ninh.luyen.github.ui.screen.detail

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
class DetailViewModel @Inject
constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {


    private val profileModel: MutableLiveData<Resource<ProfileModel>> = MutableLiveData()
    val profileLive: LiveData<Resource<ProfileModel>>
        get() = profileModel

    private val loadingBind: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLive: LiveData<Boolean>
        get() = loadingBind

    private val toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastLive: LiveData<String>
        get() = toastMessage


    fun getProfile(name: String) {
        viewModelScope.launch {
            loadingBind.value = true
            dataRepository.requestGetProfile(name).collect {
                loadingBind.value = false
                profileModel.value = it
            }
        }
    }

    fun getErrorCode(code: Int){
        val error = errorManager.getError(code)
        toastMessage.value = error.description
    }


}