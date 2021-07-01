package com.bae.roompractice.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bae.roompractice.application.JSApplication
import com.bae.roompractice.databinding.ActivityAddUserBinding
import com.bae.roompractice.model.entities.JSUser
import com.bae.roompractice.utils.SimpleLog
import com.bae.roompractice.view.viewmodel.JSUserViewModel
import com.bae.roompractice.view.viewmodel.JSUserViewModelFactory

class AddUserActivity: AppCompatActivity()
{
    private lateinit var mAddUserBinding: ActivityAddUserBinding

    private val mJSUserViewModel: JSUserViewModel by viewModels {
        JSUserViewModelFactory((application as JSApplication).repository)
    }

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
                    val name = mAddUserBinding.etUserName.text.toString().trim{it <= ' '}
                    val age = mAddUserBinding.etUserAge.text.toString().trim{it <= ' '}
                    val phone = mAddUserBinding.etUserPhone.text.toString().trim{it <= ' '}
                    var sex = ""
                    when (btnId) {
                        mAddUserBinding.rbMale.id -> {
                            sex = "MALE"
                        }
                        mAddUserBinding.rbFemale.id -> {
                            sex = "FEMALE"
                        }
                    }

                    SimpleLog.d("Data All Input ---->name : $name age : $age phone : $phone sex : $sex")
                    val user = JSUser(
                        name,
                        age,
                        phone,
                        sex
                    )

                    mJSUserViewModel.insert(user)
                    SimpleLog.d("Data Input Success")

                    finish()
                }
            }
        }
    }
}