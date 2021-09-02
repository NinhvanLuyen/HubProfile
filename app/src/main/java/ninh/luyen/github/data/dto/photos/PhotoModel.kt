package ninh.luyen.github.data.dto.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class PhotoModel (
    var id: String,
    var isFav: Boolean?,
    var isRecent: Boolean?,
    var created_at: String?,
    var updated_at: String?,
    var width: Int,
    var height: Int,
    var color: String?,
    var likes: Int?,
    var description: String?,
    var isSponsored: Boolean?,
    var user: UserModel?,
    var urls: UrlModel?,
    var links: LinkDetailModel?,
    var exif: ExifModel?,
    var location: LocationPhotoModel?,
    var story: StoryModel?,
): Parcelable

