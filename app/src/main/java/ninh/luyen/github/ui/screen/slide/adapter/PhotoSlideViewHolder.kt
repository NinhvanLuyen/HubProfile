package ninh.luyen.github.ui.screen.slide.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.databinding.ItemPhotoSlideBinding
import ninh.luyen.github.utils.loadImage
import ninh.luyen.github.utils.toFormatDecimal

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class PhotoSlideViewHolder(private val itemBinding: ItemPhotoSlideBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(photo: PhotoModel, onClickItem: (view: View) -> Unit) {
        photo.urls?.regular?.let {
            itemBinding.imPhoto.loadImage(it, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
        }
        photo.user?.profile_image?.medium?.let {
            itemBinding.imAvatar.loadImage(it, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            })
        }
        itemBinding.tvLike.text = photo.likes?.toDouble().toFormatDecimal()

        itemBinding.rlRecipeItem.setOnClickListener {
            onClickItem.invoke(itemBinding.imAvatar)
        }
    }
}
