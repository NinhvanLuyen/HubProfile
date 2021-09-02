package ninh.luyen.github.data.local.mapers

import com.squareup.moshi.Moshi
import ninh.luyen.github.data.dto.photos.*
import ninh.luyen.github.data.local.entites.PhotoEntity

/**
 * Created by luyen_ninh on 8/29/21.
 */

fun PhotoEntity.toPhotoModel(moshi: Moshi): PhotoModel {
    return PhotoModel(
        id,
        isFav,
        isRecent,
        created_at,
        updated_at,
        width,
        height,
        color,
        likes,
        description,
        isSponsored,
        moshi.adapter(UserModel::class.java).fromJson(user ?: "{}"),
        moshi.adapter(UrlModel::class.java).fromJson(urls ?: "{}"),
        moshi.adapter(LinkDetailModel::class.java).fromJson(links ?: "{}"),
        moshi.adapter(ExifModel::class.java).fromJson(exif ?: "{}"),
        moshi.adapter(LocationPhotoModel::class.java).fromJson(location ?: "{}"),
        moshi.adapter(StoryModel::class.java).fromJson(story ?: "{}")
    )

}

fun PhotoModel.toPhotoEntity(moshi: Moshi): PhotoEntity {
    return PhotoEntity(
        id,
        isFav,
        isRecent,
        created_at,
        updated_at,
        width,
        height,
        color,
        likes,
        description,
        isSponsored,
        moshi.adapter(UserModel::class.java).toJson(user),
        moshi.adapter(UrlModel::class.java).toJson(urls),
        moshi.adapter(LinkDetailModel::class.java).toJson(links),
        moshi.adapter(ExifModel::class.java).toJson(exif),
        moshi.adapter(LocationPhotoModel::class.java).toJson(location),
        moshi.adapter(StoryModel::class.java).toJson(story)
    )
}