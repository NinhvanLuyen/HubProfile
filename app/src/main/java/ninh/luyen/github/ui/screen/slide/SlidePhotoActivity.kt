package ninh.luyen.github.ui.screen.slide

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ninh.luyen.github.R
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.databinding.ActivitySlidePhotoBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.ui.screen.detail.DetailActivity
import ninh.luyen.github.ui.screen.follower.adapter.FooterLoadingAdapter
import ninh.luyen.github.ui.screen.slide.adapter.SlidePhotoPagerAdapter
import ninh.luyen.github.utils.fadeTransition
import ninh.luyen.github.utils.observe
import ninh.luyen.github.utils.showToast

@FlowPreview
@AndroidEntryPoint
class SlidePhotoActivity : BaseActivity() {

    companion object {
        fun newInstance(activity: Activity): Intent {
            return Intent(activity, SlidePhotoActivity::class.java)
        }
    }

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var viewBinding: ActivitySlidePhotoBinding
    private val viewModel: SlidePhotoViewModel by viewModels()
    private var photoAdapter: SlidePhotoPagerAdapter? = null

    override fun observeViewModel() {
        observe(viewModel.openPhotoDetail, this::openPhotoDetail)
        observe(viewModel.toastLive, this::showToast)
    }

    private fun getData(contentSearch: String) {
        lifecycleScope.launch {
            viewModel.getAllPhotos(contentSearch).collect {
                showPhoto(it)
            }
        }
    }

    private fun showPhoto(data: PagingData<UIPhotoItemModel>) {
        photoAdapter?.submitData(lifecycle, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fadeTransition()
        initAdapter()
        defaultSearch()
        setupScreenType()
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun setupScreenType() {
        if (!resources.getBoolean(R.bool.isTablet)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun defaultSearch() {
        getData("natural")
    }

    private fun initAdapter() {

        val spanCount = 1
        layoutManager = GridLayoutManager(this, spanCount)
        viewBinding.rvPhotos.layoutManager = layoutManager
        viewBinding.rvPhotos.setHasFixedSize(true)
        photoAdapter = SlidePhotoPagerAdapter()
        viewBinding.rvPhotos.apply {

            adapter = photoAdapter?.withLoadStateFooter(
                footer = FooterLoadingAdapter { photoAdapter?.retry() })
        }
        photoAdapter?.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                viewBinding.rvPhotos.scrollToPosition(0)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        }
        viewBinding.rvPhotos.recycledViewPool.setMaxRecycledViews(0,1)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(viewBinding.rvPhotos)
    }

    private fun openPhotoDetail(photoViewData: Pair<View, PhotoModel>) {

        val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            photoViewData.first,
            ViewCompat.getTransitionName(photoViewData.first) ?: return
        )
        startActivity(DetailActivity.newInstance(this, photoViewData.second), option.toBundle())
    }

    private fun showToast(message: String) {
        viewBinding.root.showToast(message, Toast.LENGTH_SHORT)
    }

    override fun initViewBinding() {
        viewBinding = ActivitySlidePhotoBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
    }
}
