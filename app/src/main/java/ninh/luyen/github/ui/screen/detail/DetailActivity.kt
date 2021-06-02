package ninh.luyen.github.ui.screen.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ninh.luyen.github.MYNAME
import ninh.luyen.github.R
import ninh.luyen.github.data.Resource
import ninh.luyen.github.data.dto.profile.ProfileModel
import ninh.luyen.github.databinding.ActivityMainBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.ui.screen.follower.FollowersActivity
import ninh.luyen.github.utils.loadImage
import ninh.luyen.github.utils.observe
import ninh.luyen.github.utils.showOrGoneByCondition
import ninh.luyen.github.utils.showToast

@AndroidEntryPoint
class DetailActivity : BaseActivity() {


    companion object {
        private var INTENT_KEY_NAME = "NAME"
        fun newInstance(activity: Activity, name: String): Intent {
            return Intent(activity, DetailActivity::class.java)
                .apply {
                    putExtra(INTENT_KEY_NAME, name)
                }
        }
    }


    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun observeViewModel() {
        observe(viewModel.profileLive, this::bindData)
        observe(viewModel.loadingLive, this::showLoading)
        observe(viewModel.toastLive, this::showToast)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra(INTENT_KEY_NAME)?: MYNAME

        //Hardcode for backButton
        intent.getStringExtra(INTENT_KEY_NAME)?.run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        viewModel.getProfile(name)
    }


    private fun bindData(resource: Resource<ProfileModel>) {
        when (resource) {
            is Resource.Success -> {
                resource.data?.let { profileModel ->
                    viewBinding.imAvatar.loadImage(profileModel.avatar_url)
                    viewBinding.name.text = profileModel.login
                    viewBinding.tvEmail.text =
                        getString(R.string.profile_email, profileModel.email)
                    viewBinding.tvBio.text = getString(R.string.profile_bio, profileModel.bio)
                    viewBinding.tvFollowers.text =
                        getString(R.string.profile_followers, profileModel.followers ?: 0)
                    viewBinding.tvFollowers.setOnClickListener {
                        startActivity(
                            FollowersActivity.newInstance(
                                this,
                                profileModel.login?:return@setOnClickListener
                            )
                        )
                    }
                }
            }
            is Resource.DataError -> {
                resource.errorCode?.let { viewModel.getErrorCode(it) }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        viewBinding.pbLoading.showOrGoneByCondition(isLoading)
    }

    private fun showToast(message: String) {
        viewBinding.root.showToast(message, Toast.LENGTH_SHORT)
    }

    override fun initViewBinding() {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
    }

}