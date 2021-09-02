package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = false)
@Parcelize
data class LinksModel(
    var self: String?,
    var html: String?,
    var photos: String?,
    var likes: String?,
    var portfolio: String?,
    var following: String?,
    var followers: String
): Parcelable

