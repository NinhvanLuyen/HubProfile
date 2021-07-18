package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class LinkDetailModel(
    var self: String?,
    var html: String?,
    var download: String?,
    var download_location: String?,
): Parcelable