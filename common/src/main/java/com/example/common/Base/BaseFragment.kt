package com.example.common.Base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding>(
    private val currentFragmentTxnId: String = ""
) : Fragment(){
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    private var commonDialog: CommonDialog? = null
    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) { //允許權限後觸發
                Log.d("*******", "granted")
            }else{  //拒絕權限
                commonDialog?.showDialog(
                    title = "啟用權限",
                    content = "此應用程式需要啟用權限方可正常運作",
                    onCancel = {
                        goSettings()
                    }
                )
            }
        }

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
        commonDialog = CommonDialog(activity)

        onViewInit()

//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    abstract fun onViewInit()

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

    fun checkPermission(permission: String){
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    permission
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(permission))
        }
    }

    private fun requestPermissions(array: Array<String>) {
        permReqLauncher.launch(array)
    }

    /**開啟應用程式資訊 方便手動啟用權限*/
    private fun goSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}