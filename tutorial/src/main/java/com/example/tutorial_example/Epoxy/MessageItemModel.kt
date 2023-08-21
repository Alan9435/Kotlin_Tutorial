package com.example.tutorial_example.Epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.tutorial_example.R

@EpoxyModelClass
abstract class MessageItemModel: EpoxyModelWithHolder<MessageItemModel.Holder>() {
    @EpoxyAttribute
    lateinit var tvSubTitle: String

    @EpoxyAttribute
    lateinit var tvContent: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvSubTitle.text = tvSubTitle
        holder.tvContent.text = tvContent
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_message
    }

    class Holder: EpoxyHolder(){
        lateinit var tvSubTitle: TextView
        lateinit var tvContent: TextView

        override fun bindView(itemView: View) {
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle)
            tvContent = itemView.findViewById(R.id.tvContent)
        }

    }
}

data class MessageData(
    val subTitle: String? = null,
    val content: String? = null
)
