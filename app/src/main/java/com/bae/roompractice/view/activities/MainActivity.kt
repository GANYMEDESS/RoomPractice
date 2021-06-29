package com.bae.roompractice.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bae.roompractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var mMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        mMainBinding.btnAdd.setOnClickListener {
            val addUserIntent = Intent(this, AddUserActivity::class.java)
            startActivity(addUserIntent)
        }
    }
}