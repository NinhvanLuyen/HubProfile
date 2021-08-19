package ninh.luyen.github.ui.screen.slide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.databinding.ItemPhotoSlideBinding
import ninh.luyen.github.ui.screen.follower.PhotosViewModel
import ninh.luyen.github.ui.screen.slide.SlidePhotoViewModel

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class SlidePhotoPagerAdapter :
    PagingDataAdapter<UIPhotoItemModel, PhotoSlideViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: PhotoSlideViewHolder, position: Int) {
        val photoModel: UIPhotoItemModel? = getItem(position)

        // Note that "concert" is a placeholder if it's null.
        if (photoModel is UIPhotoItemModel.Data) {
            holder.bind(photo = photoModel.photoModel) {

            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<UIPhotoItemModel>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldPhoto: UIPhotoItemModel,
                newPhoto: UIPhotoItemModel
            ): Boolean {
                return if (oldPhoto is UIPhotoItemModel.Data
                    && newPhoto is UIPhotoItemModel.Data
                ) {
                    oldPhoto.photoModel.id == newPhoto.photoModel.id
                } else {
                    false
                }
            }

            override fun areContentsTheSame(
                oldProfile: UIPhotoItemModel,
                newProfile: UIPhotoItemModel
            ) = oldProfile == newProfile
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoSlideViewHolder {
        val itemBinding =
            ItemPhotoSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoSlideViewHolder(itemBinding)
    }
}