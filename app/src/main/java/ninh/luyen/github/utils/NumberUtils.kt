package ninh.luyen.github.utils

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * Created by luyen_ninh on 19/08/2021.
 */
fun Double?.toFormatDecimal(): String {
    if (this == null)
        return "0"
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val valueData = floor(log10(this)).toInt()
    val base = valueData / 3
    return if (this >= 3 && base < suffix.size) {
        (DecimalFormat("#,###,###").format(
            this / 10.0.pow(base.toDouble() * 3)
        ) + suffix[base]).replace(",", ".")
    } else {
        DecimalFormat("#,###,###").format(this).replace(",", ".")
    }
}

