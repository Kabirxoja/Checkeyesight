package uz.kabir.checkeyesight.custom

import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class CustomPicker(
    private val hourEt: EditText,
    private val minuteEt: EditText
) {

    private var hour = 8
    private var minute = 0

    init {
        hourEt.filters = arrayOf(MinMaxFilter(0, 23))
        minuteEt.filters = arrayOf(MinMaxFilter(0, 59))

        hourEt.addTextChangedListener { it ->
            hour = it.toString().toIntOrNull()?.coerceIn(0, 23) ?: hour
        }

        minuteEt.addTextChangedListener { it ->
            minute = it.toString().toIntOrNull()?.coerceIn(0, 59) ?: minute
        }

        updateUI()
    }

    fun setTime(h: Int, m: Int) {
        hour = h.coerceIn(0, 23)
        minute = m.coerceIn(0, 59)
        updateUI()
    }

    fun incHour() {
        hour = (hour + 1) % 24
        updateUI()
    }

    fun decHour() {
        hour = if (hour == 0) 23 else hour - 1
        updateUI()
    }

    fun incMinute() {
        minute = (minute + 1) % 60
        updateUI()
    }

    fun decMinute() {
        minute = if (minute == 0) 59 else minute - 1
        updateUI()
    }

    fun getTime(): Pair<Int, Int> {
        syncFromInput()
        return hour to minute
    }

    private fun syncFromInput() {
        hour = hourEt.text.toString().toIntOrNull()?.coerceIn(0, 23) ?: hour
        minute = minuteEt.text.toString().toIntOrNull()?.coerceIn(0, 59) ?: minute
    }

    private fun updateUI() {
        hourEt.setText(String.format("%02d", hour))
        minuteEt.setText(String.format("%02d", minute))
        hourEt.setSelection(hourEt.text.length)
        minuteEt.setSelection(minuteEt.text.length)
    }
}