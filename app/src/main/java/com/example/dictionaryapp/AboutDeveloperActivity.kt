package com.example.dictionaryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.about_developer.*

class AboutDeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_developer)
        linkdin.isClickable = true
        github.isClickable = true
        instagram.isClickable = true
        linkdin.setOnClickListener {
            val browseIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://www.linkedin.com/in/simson-raj-36b197219"))
            startActivity(browseIntent)
        }
        github.setOnClickListener {
            val browseIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/simson4761"))
            startActivity(browseIntent)
        }
        instagram.setOnClickListener {
            val browseIntent  = Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/simson.mp4/"))
            startActivity(browseIntent)
        }
        back.setOnClickListener {
            val backIntent = Intent(applicationContext,MainActivity::class.java)
            startActivity(backIntent)
        }
    }
}