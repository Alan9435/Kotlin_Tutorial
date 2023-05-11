package com.example.tutorial.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.Base.BaseFragment
import com.example.common.Base.InterfaceCallback
import com.example.tutorial.Fragment.Adapter.ItemData
import com.example.tutorial.Fragment.Adapter.RecycleViewAdapter
import com.example.tutorial.databinding.FragmentRecycleviewBinding

class RecycleViewFragment: BaseFragment<FragmentRecycleviewBinding>(),InterfaceCallback {
    lateinit var itemList: List<ItemData>
    override fun onViewInit() {
        itemList = listOf(
            ItemData("t1","b1"),
            ItemData("t2","b2"),
            ItemData("t3","b3"),
            ItemData("t4","b4"))
        binding.rvMyItems.apply {
            adapter = RecycleViewAdapter(itemList).apply{
                setListen(this@RecycleViewFragment)
                /**
                 * this@RecycleViewFragment 表示當前 RecycleViewFragment 的實例，
                 * 其中 this 代表當前的對象，@RecycleViewFragment
                 * 則指定了該對象所在的類型。因此，this@RecycleViewFragment 表示當前 RecycleViewFragment 的實例
                 * 由於 RecycleViewFragment 實現了這個接口，
                 * 因此可以直接將自己的實例作為參數傳遞，從而實現在回調方法中對當前 RecycleViewFragment 的一些操作。*/
            }
            layoutManager = LinearLayoutManager(context) //設定layout顯示方式
        }

    }

    override fun getItem(itemText: String){
        binding.tvTitle.text = itemText
    }
}