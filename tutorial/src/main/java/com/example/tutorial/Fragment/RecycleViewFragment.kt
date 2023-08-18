package com.example.tutorial.Fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.Base.BaseFragment
import com.example.common.Base.InterfaceCallback
import com.example.tutorial.Fragment.Adapter.ItemData
import com.example.tutorial.Fragment.Adapter.RecycleViewAdapter
import com.example.tutorial.databinding.FragmentRecycleviewBinding
import com.example.tutorial.databinding.ItemViewBinding

/** 開Profiler觀察ListView跟RV的效能差別
 * 可以發現當你使用ListView時上下滑動記憶體並不會被回收後重用
 * 而RV由於 viewPool與viewHolder機制 使itemView可以不用一直被銷毀與創建 達到重用目的 */
class RecycleViewFragment : BaseFragment<FragmentRecycleviewBinding>(), InterfaceCallback {
    private var itemList: MutableList<ItemData> = mutableListOf()
    override fun onViewInit() {
        for (i in 0..100) {
            itemList.add(ItemData("t$i", "b$i"))
        }

        binding.rvMyItems.apply {
            adapter = RecycleViewAdapter(itemList).apply {
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

//        binding.lvMyItems.apply {
//            adapter = ListViewAdapter(context,itemList)
//        }
    }

    override fun getItem(itemText: String) {
        binding.tvTitle.text = itemText
    }
}

class ListViewAdapter(var context: Context, var data: List<ItemData>) : BaseAdapter() {
    override fun getCount() = data.size

    override fun getItem(position: Int): ItemData {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = ItemViewBinding.inflate(LayoutInflater.from(context))
        view.tvItem.text = getItem(position).itemText
        view.btnItem.apply {
            text = getItem(position).itemButtonText
        }
        return view.root
    }
}