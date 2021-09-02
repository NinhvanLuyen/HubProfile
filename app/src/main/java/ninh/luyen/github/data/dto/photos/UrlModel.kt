package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class UrlModel(
    var raw: String?,
    var full: String?,
    var regular: String?,
    var small: String?,
    var thumb: String?,
): Parcelable