package ninh.luyen.github.data.dto.profile

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Created by luyen_ninh on 01/06/2021.
 */
@JsonClass(generateAdapter = false)
@Parcelize
data class ProfileModel(
    @Json(name = "login")
    val login: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "avatar_url")
    val avatar_url: String,
    @Json(name = "html_url")
    val html_url: String,
    @Json(name = "followers_url")
    val followers_url: String,
    @Json(name = "company")
    val company: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "bio")
    val bio: String,
    @Json(name = "public_repos")
    val public_repos: String,
    @Json(name = "public_gists")
    val public_gists: String,
    @Json(name = "followers")
    val followers: String,
) : Parcelable