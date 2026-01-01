package uz.kabir.checkeyesight.custom

import android.text.InputFilter
import android.text.Spanned

class MinMaxFilter(private val min: Int, private val max: Int) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.substring(0, dstart) + source + dest.substring(dend)).toInt()
            if (input in min..max) return null
        } catch (e: Exception) {

        }
        return ""
    }
}