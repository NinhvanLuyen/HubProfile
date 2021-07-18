package ninh.luyen.github.ui.screen.follower.adapter

import androidx.recyclerview.widget.RecyclerView
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.databinding.PhotoSingleItemBinding
import ninh.luyen.github.utils.loadImage

/**
 * Created by luyen_ninh on 13/07/2021.
 */
class PhotoViewHolder(private val itemBinding: PhotoSingleItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(photo: PhotoModel, onClickItem:()->Unit) {
//        val renderEffect = RenderEffect.createBlurEffect(20.0f,20.0f, Shader.TileMode.DECAL)
        if (itemView.parent != null)
        photo.urls?.regular?.let {
            itemBinding.imAvatar.loadImage(it)
        }

        itemBinding.rlRecipeItem.setOnClickListener {
            onClickItem.invoke()
        }
    }
}
