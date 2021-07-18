package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class ExifModel(
    var make: String,
    var model: String,
    var aperture: String,
    var focal_length: String,
    var exposure_time: String,
    var iso: String
): Parcelable