package ninh.luyen.github.ui.screen.follower

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.databinding.ActivityFollwersBinding
import ninh.luyen.github.databinding.ActivityMainBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.ui.screen.detail.DetailActivity
import ninh.luyen.github.ui.screen.follower.adapter.FollowersAdapter
import ninh.luyen.github.utils.loadImage
import ninh.luyen.github.utils.observe
import ninh.luyen.github.utils.showOrGoneByCondition
import ninh.luyen.github.utils.showToast
import kotlin.reflect.jvm.internal.impl.load.java.Constant

@AndroidEntryPoint
class FollowersActivity : BaseActivity() {

    companion object {
        private var INTENT_KEY_NAME = "NAME"
        fun newInstance(activity: Activity, name: String): Intent {
            return Intent(activity, FollowersActivity::class.java)
                .apply {
                    putExtra(INTENT_KEY_NAME, name)
                }
        }
    }


    private lateinit var viewBinding: ActivityFollwersBinding
    private val viewModel: FollowersViewModel by viewModels()

    override fun observeViewModel() {
        observe(viewModel.followersLive, this::bindData)
        observe(viewModel.openProfileDetailLive, this::openProfileDetail)
        observe(viewModel.loadingLive, this::showLoading)
        observe(viewModel.toastLive, this::showToast)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val name = intent.getStringExtra(INTENT_KEY_NAME) ?: return
        val layoutManager = LinearLayoutManager(this)
        viewBinding.rvFollowers.layoutManager = layoutManager
        viewBinding.rvFollowers.setHasFixedSize(true)
        viewModel.getFollowers(name)
    }


    private fun bindData(resource: Resource<List<ProfileModel>>) {
        when (resource) {
            is Resource.Success -> {
                resource.data?.let { followers ->
                    viewBinding.rvFollowers.adapter = FollowersAdapter(viewModel, followers)
                }
            }
            is Resource.DataError -> {
                resource.errorCode?.let { viewModel.getErrorCode(it) }
            }
        }
    }

    private fun openProfileDetail(profileModel: ProfileModel)
    {
        startActivity(DetailActivity.newInstance(this, profileModel.login?:return))
    }

    private fun showLoading(isLoading: Boolean) {
        viewBinding.pbLoading.showOrGoneByCondition(isLoading)
    }

    private fun showToast(message: String) {
        viewBinding.root.showToast(message, Toast.LENGTH_SHORT)
    }

    override fun initViewBinding() {
        viewBinding = ActivityFollwersBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
    }

}