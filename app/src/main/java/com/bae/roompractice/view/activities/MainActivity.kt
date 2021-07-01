package com.bae.roompractice.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bae.roompractice.application.JSApplication
import com.bae.roompractice.databinding.ActivityMainBinding
import com.bae.roompractice.utils.SimpleLog
import com.bae.roompractice.view.adapters.JSUserAdapter
import com.bae.roompractice.view.viewmodel.JSUserViewModel
import com.bae.roompractice.view.viewmodel.JSUserViewModelFactory

class MainActivity : AppCompatActivity()
{
    private lateinit var mMainBinding: ActivityMainBinding
    private lateinit var mJSUserAdapter: JSUserAdapter
    private val mJSUserViewModel: JSUserViewModel by viewModels {
        JSUserViewModelFactory((application as JSApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        mJSUserAdapter = JSUserAdapter(this)
        mMainBinding.rvUsersList.layoutManager = LinearLayoutManager(this)
        mMainBinding.rvUsersList.adapter = mJSUserAdapter

        mMainBinding.btnAdd.setOnClickListener {
            val addUserIntent = Intent(this, AddUserActivity::class.java)
            startActivity(addUserIntent)
        }

        mJSUserViewModel.allUserList.observe(this) { users ->
            users.let {
                if(it.isNotEmpty()) {
                    mMainBinding.rvUsersList.visibility = View.VISIBLE
                    mMainBinding.tvNoUsersAddedYet.visibility = View.GONE

                    mJSUserAdapter.usersList(it)
                    SimpleLog.i("User is Not Empty")
                }
                else {
                    mMainBinding.rvUsersList.visibility = View.GONE
                    mMainBinding.tvNoUsersAddedYet.visibility = View.VISIBLE
                    SimpleLog.i("User is Empty :(")
                }
            }
        }
    }
}