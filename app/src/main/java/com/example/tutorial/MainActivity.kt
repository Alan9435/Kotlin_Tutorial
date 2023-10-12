package com.example.tutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Base.InterfaceCallback
import com.example.tutorial_example.Compose.ComposeActivity
import com.example.tutorial_example.Fragment.Adapter.ItemData
import com.example.tutorial_example.Fragment.Adapter.RecycleViewAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //跳頁至compose畫面
        startActivity(Intent(this,ComposeActivity::class.java))
        //todo Collapsing Effect、Data binding Nowinandroid
    }
}