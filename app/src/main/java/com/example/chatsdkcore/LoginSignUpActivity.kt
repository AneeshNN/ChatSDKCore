package com.example.chatsdkcore


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatsdkcore.auth.AddUserViewModel
import com.example.chatsdkcore.base.BaseActivity
import com.example.chatsdkcore.databinding.ActivityLoginSignUpBinding
import androidx.lifecycle.Observer

class LoginSignUpActivity : BaseActivity(), onBaseListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginSignUpBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_login_sign_up
            )

        val signUpLoginViewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        binding.loginVM = signUpLoginViewModel
        binding.lifecycleOwner = this

        /**
         * Checks whether user is logged inn or not
         */
        signUpLoginViewModel.checkUserLogin().observe(this, Observer {
            Log.d("What", "$it")
            if (it) {
                showToast("Sign in success")
            }
        })

        /**
         * Listen to toast messages, showing toast messages
         */
        signUpLoginViewModel.toastMessagesLiveData().observe(this, Observer {
            showToast(it)
        })
    }
    /**
     * Shows toast
     * @param msg The message for the toast to show
     */
    override fun showToast(msg: String) {
        if (!msg.isNullOrEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
//    override fun getLayoutResourceId(): Int {
//
//
//
//        return R.layout.activity_login_sign_up
//    }
}