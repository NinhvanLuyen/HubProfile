package ninh.luyen.github.data.remote.factory

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import javax.annotation.Nullable

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString
class NullToEmptyStringAdapter {

    @ToJson
    fun toJson(@NullToEmptyString value: String?): String? {
        return value
    }
    @FromJson
    @NullToEmptyString
    fun fromJson(@Nullable value: String?): String {
        return value?:""
    }


}