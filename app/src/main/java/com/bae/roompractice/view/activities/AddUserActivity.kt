package com.bae.roompractice.view.activities

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bae.roompractice.databinding.ActivityAddUserBinding
import com.bae.roompractice.utils.SimpleLog

class AddUserActivity: AppCompatActivity()
{
    private lateinit var mAddUserBinding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAddUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(mAddUserBinding.root)

        mAddUserBinding.btnAddUser.setOnClickListener {
            val btnId = mAddUserBinding.radioGroup.checkedRadioButtonId

            when {
                mAddUserBinding.etUserName.text.isEmpty() -> {
                    SimpleLog.i("Name Empty")
                    Toast.makeText(this@AddUserActivity,
                    "Name is Empty",
                    Toast.LENGTH_LONG
                    ).show()
                }
                mAddUserBinding.etUserAge.text.isEmpty() -> {
                    SimpleLog.i("Age Empty")
                    Toast.makeText(this@AddUserActivity,
                        "Age is Empty",
                        Toast.LENGTH_LONG
                    ).show()
                }
                mAddUserBinding.etUserPhone.text.isEmpty() -> {
                    SimpleLog.i("Phone Empty")
                    Toast.makeText(this@AddUserActivity,
                        "Phone is Empty",
                        Toast.LENGTH_LONG
                    ).show()
                }
                btnId == -1 -> {
                    SimpleLog.i("Sex Empty")
                    Toast.makeText(this@AddUserActivity,
                        "Sex is Empty",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    SimpleLog.i("All Clear Add User Info")
                }
            }
        }
    }
}