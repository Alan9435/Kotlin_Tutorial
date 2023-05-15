package com.example.common.Base

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseDialogFragment<VB : ViewBinding>(
    var _activity: FragmentActivity?,
    var _width: Int = (Resources.getSystem().displayMetrics.widthPixels * 0.90).toInt(),
    var _height: Int = (Resources.getSystem().displayMetrics.heightPixels * 0.30).toInt()
) : DialogFragment(),
    LifecycleObserver {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getMethod().invoke(null, layoutInflater, container, false) as VB
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewInit()
    }

    abstract fun onViewInit()

    open fun showDialog() {
        _activity?.supportFragmentManager?.let {
            if (this.isVisible.not()) {
                this.show(it, null) /**tag參數傳遞null 避免顯示重複的Fragment 同時保證只存在一個實例子*/
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        setDialogBound()
    }

    /**
     * 創建自訂義Dialog
     * 如果當前dialog有focus則隱藏鍵盤*/
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
                if (currentFocus != null) {
                    val inputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                return super.dispatchTouchEvent(motionEvent)
            }
        }
    }

    private fun setDialogBound() = dialog?.window?.setLayout(_width, _height) //設定dialog大小

    private fun getMethod(): Method {
        //利用反射，呼叫指定ViewBinding中的inflate方法填充檢視
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        return clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
    }
}