package com.example.weather_application

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class About: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        instagram()
        linkedin()
        gmail()
        website()
    }
    private fun instagram(){
        val img = findViewById<View>(R.id.instagram) as ImageView
        img.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://www.instagram.com/saksham_loona/"))
            startActivity(intent)
        }
    }
    private fun linkedin(){
        val img = findViewById<View>(R.id.linkedin) as ImageView
        img.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://linkedin.com/in/saksham-loona-716248291/"))
            startActivity(intent)
        }
    }
    private fun website(){
        val img = findViewById<View>(R.id.website) as ImageView
        img.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("https://sakshamloona9.wixsite.com/my-site/"))
            startActivity(intent)
        }
    }
    private fun gmail(){
        val img = findViewById<View>(R.id.gmail) as ImageView
        img.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.setData(Uri.parse("mailto:sakshamloona9@gmail.com"))
            startActivity(intent)
        }
    }
}