package ninh.luyen.github.ui.screen.follower.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.databinding.FollowerItemBinding
import ninh.luyen.github.ui.screen.follower.FollowersViewModel


/**
 * Created by luyen_ninh on 01/06/2021
 */

class FollowersAdapter(private val followersViewModel: FollowersViewModel, private val followers: List<ProfileModel>) : RecyclerView.Adapter<FollowerViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(profile: ProfileModel) {
            followersViewModel.openProfileOfFollower(profile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val itemBinding = FollowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(followers[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return followers.size
    }
}

