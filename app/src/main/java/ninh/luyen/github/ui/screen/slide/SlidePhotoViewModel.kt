package ninh.luyen.github.ui.screen.slide

import android.view.View
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

@HiltViewModel
class SlidePhotoViewModel @Inject
constructor(
    private val unsplashRepository: UnsplashDataSource
) : BaseViewModel() {

    private val onOpenPhoto: MutableLiveData<Pair<View, PhotoModel>> = MutableLiveData()
    val openPhotoDetail: LiveData<Pair<View, PhotoModel>>
        get() = onOpenPhoto

    private val toastMessage: MutableLiveData<String> = MutableLiveData()

    val toastLive: LiveData<String>
        get() = toastMessage

    fun getAllPhotos(query: String): Flow<PagingData<UIPhotoItemModel>> {
        return unsplashRepository.getPhotosMediator(query)
            .flow
            .map { pagingData ->
                pagingData.map {
                    UIPhotoItemModel.Data(it)
                }
            }
    }

}