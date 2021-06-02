package ninh.luyen.github.ui.screen.follower.adapter

import ninh.luyen.github.data.dto.profile.ProfileModel

/**
 * Created by luyen_ninh on 02/06/2021.
 */
interface RecyclerItemListener {
    fun onItemSelected(profile: ProfileModel)
}