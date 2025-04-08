package uz.kabir.checkeyesight.db

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.kabir.checkeyesight.R


class DataBaseRVA(private val list: MutableList<User>): RecyclerView.Adapter<DataBaseRVA.ViewHolder>() {

    private lateinit var listener: OnUserClickedListener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item_db,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]

        val leftEyeResult = holder.itemView.findViewById<TextView>(R.id.left_eye_result)
        val rightEyeResult = holder.itemView.findViewById<TextView>(R.id.right_eye_result)
        val dataView = holder.itemView.findViewById<TextView>(R.id.date_view)
        val deleteBtn = holder.itemView.findViewById<ImageView>(R.id.delete)

        leftEyeResult.text = user.leftEye
        rightEyeResult.text = user.rightEye
        dataView.text = user.dateFormat


        deleteBtn.setOnClickListener {
            if (position != RecyclerView.NO_POSITION){
                listener.onUserClicked(position)

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnUserClickedListener(listener: OnUserClickedListener){
        this.listener = listener
    }

    interface OnUserClickedListener{
        fun onUserClicked(position: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteData(user: User){
        list.remove(user)
        notifyDataSetChanged()
    }
}