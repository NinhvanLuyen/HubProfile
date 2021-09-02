package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class LinkDetailModel(
    var self: String?,
    var html: String?,
    var download: String?,
    var download_location: String?,
): Parcelable