package ninh.luyen.github.data.dto.photos

/**
 * Created by luyen_ninh on 14/07/2021.
 */
sealed class UIPhotoItemModel {
    object Footer : UIPhotoItemModel()
    class Data(val photoModel: PhotoModel) : UIPhotoItemModel()
}