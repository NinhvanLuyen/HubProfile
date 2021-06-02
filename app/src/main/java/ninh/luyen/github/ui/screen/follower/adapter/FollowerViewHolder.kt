package ninh.luyen.github.ui.screen.follower.adapter

import androidx.recyclerview.widget.RecyclerView
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.databinding.FollowerItemBinding
import ninh.luyen.github.utils.loadImage

/**
 * Created by luyen_ninh on 01/06/2021
 */

class FollowerViewHolder(private val itemBinding: FollowerItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(profileMode: ProfileModel, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvBio.text = profileMode.bio
        itemBinding.tvName.text = profileMode.login
        itemBinding.imAvatar.loadImage(profileMode.avatar_url)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(profileMode) }
    }
}

