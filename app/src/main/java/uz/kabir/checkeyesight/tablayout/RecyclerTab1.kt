package uz.kabir.checkeyesight.tablayout

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.kabir.checkeyesight.R


class RecyclerTab1() : RecyclerView.Adapter<RecyclerTab1.ViewHolder>() {

    private var dataList = emptyList<DataModelTab>()
    private var listenerTabFirst: OnItemClickedListener? = null

    internal fun setDataList(dataList: List<DataModelTab>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var info: TextView
        var adsImage: ImageView

        init {
            image = itemView.findViewById(R.id.image_tab1)
            title = itemView.findViewById(R.id.title_tab1)
            info = itemView.findViewById(R.id.info_tab1)
            adsImage = itemView.findViewById(R.id.ads_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tab1_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.title.text = data.title
        holder.image.setImageResource(data.image)
        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                listenerTabFirst?.onClickedItem(position)
            }
        }
        holder.info.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition!=RecyclerView.NO_POSITION)
            listenerTabFirst?.onClickedInfo(position)
        }
        if(data.ads)
            holder.adsImage.visibility = View.VISIBLE
        else
            holder.adsImage.visibility = View.GONE
    }

    override fun getItemCount() = dataList.size

    interface OnItemClickedListener {
        fun onClickedItem(position: Int)
        fun onClickedInfo(position: Int)
    }

    fun setOnClickListener(listenerUssd: OnItemClickedListener) {
        this.listenerTabFirst = listenerUssd
    }
}