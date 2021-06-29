package com.bae.roompractice.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bae.roompractice.databinding.ActivityAddUserBinding

class AddUserActivity: AppCompatActivity()
{
    lateinit var mAddUserBinding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAddUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(mAddUserBinding.root)
    }
}