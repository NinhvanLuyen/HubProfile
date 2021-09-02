package ninh.luyen.github.data.local.entites

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ninh.luyen.github.data.dto.photos.*

/**
 * Created by luyen_ninh on 20/08/2021.
 * @param  user: UserModel?,
 * @param urls: UrlModel?,
 * @param links: LinkDetailModel?,
 * @param exif: ExifModel?,
 * @param location: LocationPhotoModel?,
 * @param story: StoryModel?,
 */
@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val isFav: Boolean?,
    val isRecent: Boolean?,
    val created_at: String?,
    val updated_at: String?,
    val width: Int,
    val height: Int,
    val color: String?,
    val likes: Int?,
    val description: String?,
    val isSponsored: Boolean?,
    val user: String?,
    val urls: String?,
    val links: String?,
    val exif: String?,
    val location: String?,
    val story: String?,
)