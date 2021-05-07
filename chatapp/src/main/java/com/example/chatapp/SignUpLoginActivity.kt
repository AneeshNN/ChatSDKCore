package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatsdkcore.LoginSignUpActivity
import com.example.chatsdkcore.SignUpListener
import com.example.chatsdkcore.auth.AuthRepository
import kotlinx.android.synthetic.main.activity_main.*

class SignUpLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )
        val signUpLoginViewModel = ViewModelProvider(this).get(SignUpLoginViewModel::class.java)
        binding.loginVM = signUpLoginViewModel
        binding.lifecycleOwner = this
//        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            signUpLoginViewModel.getAndVerifyOTP()
        }



    }
}
