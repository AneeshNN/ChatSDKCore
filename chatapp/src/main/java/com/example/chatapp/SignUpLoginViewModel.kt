package com.example.chatapp

/**
 * Created by Aneesh NN on 3/24/21.
 */
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.chatsdkcore.SignUpListener
import com.example.chatsdkcore.constants.FirebaseRefKeys
import com.example.chatsdkcore.auth.AuthRepository
import com.example.chatsdkcore.auth.BaseViewModel

/**
 * ViewModel for home activity - has functionality as sign in with verification - user can enter phone number and after verification, can get registered
 * Here, entering phone number and entering verification code are handled on the same page
 */
open class SignUpLoginViewModel : BaseViewModel() {
    private var verificationID: String? = null
    private lateinit var signUpListener: SignUpListener

    var editTextValue = MutableLiveData<String>().apply {
        value = FirebaseRefKeys.EMPTY_MESSAGE
    }
    var editTextHint = MutableLiveData<String>().apply {
        value = FirebaseRefKeys.ENTER_PHONE
    }
    var progressbarVisibility = MutableLiveData<Int>().apply {
        value = 8
    }
    var buttonText = MutableLiveData<String>().apply {
        value = FirebaseRefKeys.SUBMIT
    }
    /**
     * Validating phone number field when login button clicks
     */
    fun getAndVerifyOTP() {
        if (buttonText.value == FirebaseRefKeys.VERIFY) {
            verifyOTP()
        } else {
            val phoneNumber = editTextValue.value!!.trim()
            if (phoneNumber.length == 13) {
                getOTP(phoneNumber)
            }else{
                toastMsg.value = FirebaseRefKeys.ENTER_VALID_NUMBER
            }
        }
    }

    fun getOTP(phoneNumber: String) {
        showProgress()
        AuthRepository.instance.verifyNumber(
            phoneNumber,
            object : SignUpListener {
                override fun onSuccess(data: Any) {
                    Log.e("testlog", "onsuccess111      " + data.toString())
                    hideProgress(FirebaseRefKeys.VERIFY)
                }


                override fun onFailure(error: Any) {
                    hideProgress(FirebaseRefKeys.EMPTY_MESSAGE)
                    Log.e("testlog", "onFailureeeee  111      " + error.toString())
                }
            })
    }

    fun verifyOTP() {
        val OTP = editTextValue.value!!.trim()
        if (!OTP.isNullOrEmpty()) {
            showProgress()
            AuthRepository.instance.verifyOTPAndSignIn(
                OTP,
                object : SignUpListener {
                    override fun onSuccess(data: Any) {
                        Log.e("testlog", "sign in sucesssss-->      " + data.toString())
                        hideProgress(FirebaseRefKeys.EMPTY_MESSAGE)
                    }


                    override fun onFailure(error: Any) {
                        Log.e("testlog", "sign in failedddd-->      " + error.toString())
                        hideProgress(FirebaseRefKeys.EMPTY_MESSAGE)
                    }
                })
        }else{
            showProgress()
            toastMsg.value = FirebaseRefKeys.ENTER_VALID_NUMBER
        }
    }

    /**
     * Handles visibility of progress bar and makes button text as blank
     */
    private fun showProgress() {
        progressbarVisibility.value = 0
        buttonText.value = FirebaseRefKeys.EMPTY_MESSAGE
    }

    /**
     * Handles visibility of progress bar and makes button text as blank
     * @param text button text
     */
    private fun hideProgress(text: String) {
        progressbarVisibility.value = 8
        buttonText.value = text
    }

}