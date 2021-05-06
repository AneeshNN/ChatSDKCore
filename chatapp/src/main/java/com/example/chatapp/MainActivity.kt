package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatsdkcore.LoginSignUpActivity
import com.example.chatsdkcore.SignUpListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton.setOnClickListener {
            if (loginEditText.text.toString().trim().length == 13) {
                LoginSignUpActivity().enterPhoneNumber(
                    loginEditText.text.toString().trim(),
                    object : SignUpListener {
                        override fun onSuccess(data: Any) {
                            Log.e("testlog", "onsuccess111      " + data.toString())
                        }


                        override fun onFailure(error: Any) {
                            Log.e("testlog", "onFailureeeee  111      " + error.toString())
                        }
                    })
            }else{
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
            }
        }



    }
}
