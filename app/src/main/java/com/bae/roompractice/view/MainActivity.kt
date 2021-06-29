package com.bae.roompractice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bae.roompractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var mMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)
    }
}