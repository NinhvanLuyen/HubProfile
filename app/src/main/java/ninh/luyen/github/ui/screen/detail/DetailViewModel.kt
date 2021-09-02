package ninh.luyen.github.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ninh.luyen.github.data.ProfileRepositorySource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by luyen_ninh on 02/06/2021.
 */
@HiltViewModel
class DetailViewModel @Inject
constructor(private val dataRepository: ProfileRepositorySource) : BaseViewModel() {


    private val profileModel: MutableLiveData<PhotoModel> = MutableLiveData()
    val profileLive: LiveData<PhotoModel>
        get() = profileModel

    private val loadingBind: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLive: LiveData<Boolean>
        get() = loadingBind

    private val toastMessage: MutableLiveData<String> = MutableLiveData()
    val toastLive: LiveData<String>
        get() = toastMessage


    fun getProfile(photo: PhotoModel?) {
        viewModelScope.launch {
//
            profileModel.value = photo
        }
    }

    fun getErrorCode(code: Int){
        val error = errorManager.getError(code)
        toastMessage.value = error.description
    }


}