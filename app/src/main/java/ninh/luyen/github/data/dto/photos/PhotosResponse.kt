package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


/**
 * Created by luyen_ninh on 13/07/2021.
 */
@JsonClass(generateAdapter = false)
@Parcelize
data class PhotosResponse(
    var total: Int = 0,
    var total_pages: Int = 0,
    var results: List<PhotoModel> = arrayListOf(),
) : Parcelable
