package ninh.luyen.github.ui.screen.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ninh.luyen.github.data.UnsplashDataSource
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by luyen_ninh on 02/06/2021.
 */
@HiltViewModel
class PhotosViewModel @Inject
constructor(
    private val unsplashRepository: UnsplashDataSource
) : BaseViewModel() {

    private val onOpenPhoto: MutableLiveData<PhotoModel> = MutableLiveData()
    val openPhotoDetail: LiveData<PhotoModel>
        get() = onOpenPhoto

    private val toastMessage: MutableLiveData<String> = MutableLiveData()

    val toastLive: LiveData<String>
        get() = toastMessage

    fun getAllPhotos(): Flow<PagingData<UIPhotoItemModel>> {
        return unsplashRepository.getPhotos()
            .flow
            .map { pagingData ->
                pagingData.map {
                    UIPhotoItemModel.Data(it)
                }
            }
    }

    fun onClickOpenPhoto(photoModel: PhotoModel) {
        onOpenPhoto.value = photoModel
    }


}