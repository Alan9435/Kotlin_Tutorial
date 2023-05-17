package com.example.common.Base

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.common.databinding.FragmentCommonDialogBinding

class CommonDialog(
    private val fragmentActivity: FragmentActivity?,
) : BaseDialogFragment<FragmentCommonDialogBinding>(
    fragmentActivity
) {
    private var title: String? = null
    private var content: String? = null
    private var onCancel: (() -> Unit)? = null

    override fun onViewInit() {
        initView(title, content, onCancel)    //初始化內容 showDialog()時綁定數據
        dialog?.setCanceledOnTouchOutside(false) //禁止點擊背景取消dialog
    }

    fun showDialog(
        title: String? = null,
        content: String? = null,
        onCancel: (() -> Unit)? = null
    ) {
        this.title = title
        this.content = content
        this.onCancel = onCancel
        this.showDialog()
    }

    private fun initView(title: String?, content: String?, onCancel: (() -> Unit)?) {
        binding.tvTitle.text = title
        binding.tvContent.text = content

        binding.btnClose.apply {
            setOnClickListener {
                onCancel?.invoke()
                this@CommonDialog.dismiss()
            }
        }
    }
}