package ninh.luyen.github.ui.screen.follower

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ninh.luyen.github.R
import ninh.luyen.github.data.dto.photos.PhotoModel
import ninh.luyen.github.data.dto.photos.UIPhotoItemModel
import ninh.luyen.github.databinding.ActivityPhotosBinding
import ninh.luyen.github.ui.base.BaseActivity
import ninh.luyen.github.ui.screen.follower.adapter.FooterLoadingAdapter
import ninh.luyen.github.ui.screen.follower.adapter.PhotoPagerAdapter
import ninh.luyen.github.utils.logE
import ninh.luyen.github.utils.observe
import ninh.luyen.github.utils.showToast

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

    override fun observeViewModel() {
        observe(viewModel.openPhotoDetail, this::openPhotoDetail)
        observe(viewModel.toastLive, this::showToast)

        lifecycleScope.launch {
            viewBinding.swipeRefresh.isRefreshing = true
            getData()
        }
        viewBinding.swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private fun getData(){
        lifecycleScope.launch {
            viewModel.getAllPhotos().collect {
                showPhoto(it)
            }
        }
    }

    private fun showPhoto(data: PagingData<UIPhotoItemModel>) {
        val photoAdapter = PhotoPagerAdapter(viewModel)


        viewBinding.rvPhotos.apply {

            adapter = photoAdapter.withLoadStateFooter(
                footer = FooterLoadingAdapter { photoAdapter.retry() })
        }
        photoAdapter.submitData(lifecycle, data)

        photoAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                viewBinding.rvPhotos.scrollToPosition(0)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val name = intent.getStringExtra(INTENT_KEY_NAME) ?: return
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvPhotos.layoutManager = layoutManager

        //Todo: magic to show loading full width
        "${R.layout.item_footer}".logE()
        layoutManager.spanSizeLookup = object: SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                layoutManager.spanSizeLookup
                " $position --- ${viewBinding.rvPhotos.adapter?.getItemViewType(position)}".logE()

                if(viewBinding.rvPhotos.adapter?.getItemViewType(position) == R.layout.item_footer)
                    return 2 // meaning column = 2 //loading
                return 1 //meaning column = 1 // data
            }
        }
        viewBinding.rvPhotos.setHasFixedSize(true)
    }
    
    private fun openPhotoDetail(photoModel: PhotoModel) {
//        startActivity(DetailActivity.newInstance(this, photoModel.login ?: return))
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