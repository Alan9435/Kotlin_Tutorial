package com.example.tutorial_example.Epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.common.Base.InterfaceCallback
import com.example.tutorial_example.R

@EpoxyModelClass
abstract class HeadItemModel: EpoxyModelWithHolder<HeadItemModel.Holder>() {
    private var callback: InterfaceCallback? = null

    @EpoxyAttribute
    var icon: Int? = R.mipmap.ic_launcher_round

    @EpoxyAttribute
    lateinit var tvIcon: String

    fun setCallBack(callback: InterfaceCallback?){
        this.callback = callback
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        icon?.let { holder.ivIcon.setImageResource(it) }
        holder.tvIcon.text = tvIcon
        holder.clItemView.setOnClickListener {
            callback?.getItem(tvIcon)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_carousel
    }

    inner class Holder: EpoxyHolder(){
        lateinit var ivIcon: ImageView
        lateinit var tvIcon: TextView
        lateinit var clItemView: ConstraintLayout

        override fun bindView(itemView: View) {
            ivIcon = itemView.findViewById(R.id.ivIcon)
            tvIcon = itemView.findViewById(R.id.tvIcon)
            clItemView = itemView.findViewById(R.id.clItemView)
        }
    }
}

data class HeadData(
    val icon: Int? = null,
    val iconText: String? = null
)