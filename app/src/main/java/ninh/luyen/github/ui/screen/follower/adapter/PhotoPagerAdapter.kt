package ninh.luyen.github.ui.screen.follower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.databinding.PhotoSingleItemBinding
import ninh.luyen.github.ui.screen.follower.PhotosViewModel

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class PhotoPagerAdapter(private var viewModel: PhotosViewModel) :
    PagingDataAdapter<PhotoModel, PhotoViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoModel: PhotoModel? = getItem(position)

        // Note that "concert" is a placeholder if it's null.
        photoModel?.let {
            holder.bind(photo = it) {
                viewModel.onClickOpenPhoto(it, photoModel)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<PhotoModel>() {
            override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemBinding =
            PhotoSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(itemBinding)
    }
}