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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_sign_up.view.*

class LoginSignUpActivity : BaseActivity(), onBaseListener {
    private lateinit var signUpLoginViewModel: AddUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginSignUpBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_login_sign_up
            )
        signUpLoginViewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        binding.loginVM = signUpLoginViewModel
        binding.lifecycleOwner = this

    }

    fun enterPhoneNumber(nmbr: String, listener: SignUpListener) {
        signUpLoginViewModel.verifyNumber(nmbr, listener)
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
}