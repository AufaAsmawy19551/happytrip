package com.example.happytrip.helper

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class Navigator{
    companion object{
        fun <T: AppCompatActivity> changeActivity(context: Context, destination: Class<T>){
            val homeIntent = Intent(context, destination)
            context.startActivity(homeIntent)
        }
    }
}