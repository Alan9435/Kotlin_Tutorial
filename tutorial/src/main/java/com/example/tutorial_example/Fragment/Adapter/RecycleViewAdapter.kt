package com.example.tutorial_example.Fragment.Adapter

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Base.InterfaceCallback
import com.example.tutorial_example.R

class RecycleViewAdapter(
    private val dataList: List<ItemData>
    ): RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var listener: InterfaceCallback

    fun setListen(listener: InterfaceCallback) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //把item view 填充至viewHolder綁定

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return  MyViewHolder(view)
    }

    //處理資料
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvItem.text  = dataList[position].itemText
        holder.btnItem.apply {
            text = dataList[position].itemButtonText
            setOnClickListener {
                listener.getItem(text.toString())
            }
        }
    }
    //資料上限
    override fun getItemCount(): Int = dataList.size
}

//viewHolder 宣告item的元件
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvItem: TextView = itemView.findViewById(R.id.tvItem)
    val btnItem: Button = itemView.findViewById(R.id.btnItem)
}

data class ItemData(
    val itemText: String,
    val itemButtonText: String
)