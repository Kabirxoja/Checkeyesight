package uz.kabir.checkeyesight.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.kabir.checkeyesight.databinding.ItemAlarmBinding
import java.util.Calendar

class AlarmAdapter(
    private val items: List<AlarmEntity>,
    private val onDelete: (AlarmEntity) -> Unit,
    private val onEdit: (AlarmEntity) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.MyAlarmViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAlarmViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyAlarmViewHolder,
        position: Int
    ) {
        val currentItem = items[position]
        holder.binding.tvAlarmInfo.text = formatDays(currentItem.days) + ", " + formatTime(currentItem.hour, currentItem.minute)
        holder.binding.btnEdit.setOnClickListener {
            onEdit(currentItem)
        }
        holder.binding.btnDelete.setOnClickListener {
            onDelete(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyAlarmViewHolder(val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root)


    private fun formatDays(days: List<Int>): String {
        return days.joinToString(", ") {
            when (it) {
                Calendar.MONDAY -> "Du"
                Calendar.TUESDAY -> "Se"
                Calendar.WEDNESDAY -> "Cho"
                Calendar.THURSDAY -> "Pa"
                Calendar.FRIDAY -> "Ju"
                Calendar.SATURDAY -> "Sha"
                Calendar.SUNDAY -> "Ya"
                else -> ""
            }
        }
    }

    private fun formatTime(h: Int, m: Int): String {
        return String.format("%02d:%02d", h, m)
    }

}