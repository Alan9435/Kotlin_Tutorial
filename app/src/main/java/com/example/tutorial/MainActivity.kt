package com.example.tutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial_example.Compose.ProjectTestActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo compose套件 voyager
        //跳頁至compose畫面
        startActivity(Intent(this, ProjectTestActivity::class.java))
    }
}