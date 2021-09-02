package ninh.luyen.github.ui.screen.follower

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ninh.luyen.github.R
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.databinding.ActivityPhotosBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.ui.screen.detail.DetailActivity
import ninh.luyen.github.ui.screen.follower.adapter.FooterLoadingAdapter
import ninh.luyen.github.ui.screen.follower.adapter.PhotoPagerAdapter
import ninh.luyen.github.ui.screen.slide.SlidePhotoActivity
import ninh.luyen.github.utils.*

@FlowPreview
@AndroidEntryPoint
class PhotosActivity : BaseActivity() {

    companion object {
        private var INTENT_KEY_NAME = "NAME"
        fun newInstance(activity: Activity, name: String): Intent {
            return Intent(activity, PhotosActivity::class.java)
                .apply {
                    putExtra(INTENT_KEY_NAME, name)
                }
        }
    }


    private lateinit var layoutManager: GridLayoutManager
    private lateinit var viewBinding: ActivityPhotosBinding
    private val viewModel: PhotosViewModel by viewModels()
    private var photoAdapter: PhotoPagerAdapter? = null

    override fun observeViewModel() {
        observe(viewModel.openPhotoDetail, this::openPhotoDetail)
        observe(viewModel.toastLive, this::showToast)

        viewBinding.swipeRefresh.setOnRefreshListener {
            getData(viewBinding.searchView.query.toString())
        }

    }

    private fun getData(contentSearch: String) {
        lifecycleScope.launch {
            viewModel.getAllPhotos(contentSearch).collect {
                showPhoto(it)
            }
        }
    }

    private fun showPhoto(data: PagingData<PhotoModel>) {
        photoAdapter?.submitData(lifecycle, data.map { it })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fadeTransition()
        initAdapter()
        setupSearchView()
        defaultSearch()
        setupScreenType()
    }
    private fun setupScreenType(){
        if (!resources.getBoolean(R.bool.isTablet))
        {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun defaultSearch() {
        getData("natural")
    }

    private fun setupSearchView() {
        lifecycleScope.launch {
            viewBinding.searchView.getQueryTextChangeAsFlow()
                .debounce(500)
                .filter { query ->
                    return@filter query.isNotEmpty()
                }.distinctUntilChanged()
                .collect {
                    getData(it)
                }
        }
    }

    private fun initAdapter() {

        val spanCount = if (resources.getBoolean(R.bool.isTablet)) 3 else 2

        layoutManager = GridLayoutManager(this, spanCount)
        viewBinding.rvPhotos.layoutManager = layoutManager

        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (viewBinding.rvPhotos.adapter?.getItemViewType(position) == 1)
                    return spanCount // meaning column = 2 //loading
                return 1 //meaning column = 1 // data
            }
        }
        viewBinding.rvPhotos.setHasFixedSize(true)
        photoAdapter = PhotoPagerAdapter(viewModel)
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
    }

    private fun openPhotoDetail(photoViewData: Pair<View, PhotoModel>) {

//        val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            photoViewData.first,
//            ViewCompat.getTransitionName(photoViewData.first) ?: return
//        )
//        startActivity(DetailActivity.newInstance(this, photoViewData.second), option.toBundle())
        startActivity(SlidePhotoActivity.newInstance(this))
    }

    private fun showToast(message: String) {
        viewBinding.root.showToast(message, Toast.LENGTH_SHORT)
    }

    override fun initViewBinding() {
        viewBinding = ActivityPhotosBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)
    }
}

