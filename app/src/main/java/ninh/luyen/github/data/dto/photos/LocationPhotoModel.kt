package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class LocationPhotoModel(
    var title: String,
    var name: String,
    var city: String,
    var country: String
): Parcelable