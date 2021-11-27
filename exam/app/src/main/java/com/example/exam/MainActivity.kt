package com.example.exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle("Random People")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //send view activiity_person
        btn.setOnClickListener {
            val intent = Intent(this, Persona::class.java)
            startActivity(intent)
        }



    }




}