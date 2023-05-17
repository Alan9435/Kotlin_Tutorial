package com.example.tutorial.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.Base.BaseFragment
import com.example.common.Base.CommonDialog
import com.example.common.Base.InterfaceCallback
import com.example.tutorial.Epoxy.HeadData
import com.example.tutorial.Epoxy.HomeController
import com.example.tutorial.Epoxy.MessageData
import com.example.tutorial.R
import com.example.tutorial.databinding.FragmentExpoxyBinding

class EpoxyFragment:BaseFragment<FragmentExpoxyBinding>(),InterfaceCallback {
    private var dialog: CommonDialog? = null
    override fun onViewInit() {
        val epoxyController = HomeController(this@EpoxyFragment)

        dialog = CommonDialog(activity)

        binding.ervItems.apply {
            layoutManager = LinearLayoutManager(context)
            setController(epoxyController)
        }

        epoxyController.headItemData = listOf(
            HeadData(R.drawable.ic_baseline_anchor_24,"item01"),
            HeadData(R.drawable.ic_baseline_android_24,"item02"),
            HeadData(R.drawable.ic_baseline_sports_handball_24,"item03"),
            HeadData(R.drawable.ic_baseline_sports_kabaddi_24,"item04"),
            HeadData(R.drawable.ic_baseline_anchor_24,"item05"),
            HeadData(R.drawable.ic_baseline_android_24,"item06"),
            HeadData(R.drawable.ic_baseline_sports_handball_24,"item07"),
            HeadData(R.drawable.ic_baseline_sports_kabaddi_24,"item08")
        )

        epoxyController.messageItemData = listOf(
            MessageData("subTitle1","content1"),
            MessageData("subTitle2","content2"),
            MessageData("subTitle3","content3"),
            MessageData("subTitle4","content4"),
            MessageData("subTitle5","content5"),
            MessageData("subTitle6","content6"),
            MessageData("subTitle7","content7"),)
    }

    override fun getItem(itemText: String){
        dialog?.showDialog(title = "你選擇了",content = itemText)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog = null
    }
}