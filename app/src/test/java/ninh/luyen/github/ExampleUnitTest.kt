package ninh.luyen.github

import org.junit.Test
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val value = formatDecimalOneDigitUnitGame(120000)
        println("Helloo world : $value")
    }
    fun formatDecimalOneDigitUnitGame(value: Int?): String? {
        var value = value
        if (value == null) value = 0
        val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
        val numValue = value
        val valueData = Math.floor(Math.log10(numValue.toDouble())).toInt()
        val base = valueData / 3
        return if (value >= 3 && base < suffix.size) {
            (DecimalFormat("#,###,###").format(
                numValue / Math.pow(
                    10.0,
                    base.toDouble() * 3
                )
            ) + suffix[base]).replace(",", ".")
        } else {
            DecimalFormat("#,###,###").format(numValue).replace(",", ".")
        }
    }


}