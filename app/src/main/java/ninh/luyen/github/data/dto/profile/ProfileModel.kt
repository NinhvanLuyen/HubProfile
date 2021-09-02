package ninh.luyen.github.data.dto.profile

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import ninh.luyen.github.data.remote.factory.NullToEmptyString

/**
 * Created by luyen_ninh on 01/06/2021.
 */
@JsonClass(generateAdapter = false)
@Parcelize
data class ProfileModel(
    @Json(name = "login")
    val login: String ="",
    @Json(name = "avatar_url")
    val avatar_url: String ="",
    @Json(name = "email")
    @NullToEmptyString
    val email: String ="",
    @Json(name = "bio")
    @NullToEmptyString
    val bio: String ="",
    @Json(name = "followers")
    val followers: Int?,
) : Parcelable