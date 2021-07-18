package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class UserModel(
    var id: String?,
    var updated_at: String?,
    var username: String?,
    var name: String?,
    var first_name: String?,
    var last_name: String?,
    var twitter_username: String?,
    var portfolio_url: String?,
    var bio: String?,
    var location: String?,
    var total_likes: Int,
    var total_photos: Int,
    var total_collections: Int,
    var profile_image: ProfileImageModel?,
    var links: LinksModel?,
): Parcelable