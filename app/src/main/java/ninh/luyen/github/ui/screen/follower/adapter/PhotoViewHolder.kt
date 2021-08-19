package ninh.luyen.github.ui.screen.follower.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.databinding.PhotoSingleItemBinding
import ninh.luyen.github.utils.loadImage
import ninh.luyen.github.utils.logE
import java.lang.Exception

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class PhotoViewHolder(private val itemBinding: PhotoSingleItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(photo: PhotoModel, onClickItem: (view: View) -> Unit) {
        photo.urls?.small?.let {
            itemBinding.imPhoto.loadImage(it, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
        }

        itemBinding.rlRecipeItem.setOnClickListener {
            onClickItem.invoke(itemBinding.imPhoto)
        }
    }
}
