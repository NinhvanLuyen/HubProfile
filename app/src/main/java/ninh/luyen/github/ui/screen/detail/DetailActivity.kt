package ninh.luyen.github.ui.screen.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.databinding.ActivityMainBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.utils.*
import android.view.WindowManager

import android.os.Build
import android.view.Window
import com.squareup.picasso.Callback
import java.lang.Exception


@AndroidEntryPoint
class DetailActivity : BaseActivity() {


    companion object {
        private var INTENT_KEY_NAME = "NAME"
        fun newInstance(activity: Activity, photo: PhotoModel): Intent {
            return Intent(activity, DetailActivity::class.java)
                .apply {
                    putExtra(INTENT_KEY_NAME, photo)
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
        fadeTransition()
        val photo = intent.getParcelableExtra<PhotoModel>(INTENT_KEY_NAME)

        viewModel.getProfile(photo)
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }


    private fun bindData(photo: PhotoModel) {

        photo.urls?.small?.let { url ->
            viewBinding.imPhoto.loadImage(url, object : Callback {
                override fun onSuccess() {
                    photo.urls?.regular?.let { viewBinding.imPhoto.loadImage(it) }
                }

                override fun onError(e: Exception?) {

                }

            })
        }

        viewBinding.container.setBackgroundColor(Color.parseColor(photo.color))
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