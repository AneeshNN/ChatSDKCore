package com.example.chatsdkcore.auth

/**
 * Created by Aneesh NN on 5/4/21.
 */

import com.example.chatsdkcore.SignUpListener
import com.example.chatsdkcore.constants.FirebaseRefKeys
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

/**
 * Class for managing auth processes like phone number / email verification with firebase auth
 */
class AuthRepository {
    private val authInstance = FirebaseAuth.getInstance()
    private lateinit var signUpListener: SignUpListener
    private var verificationID: String? = null

    companion object {
        private var INSTANCE: AuthRepository? = null
        val instance: AuthRepository
            get() {
                if (INSTANCE == null) {
                    INSTANCE = AuthRepository()
                }
                return INSTANCE!!
            }
    }

    /**
     * Getting auth instance
     */
    fun getAuthInstance(): FirebaseAuth {
        return authInstance
    }

        /**
     * If signIn task is success, creates a user in firebase auth section with the phone number entered,
     * and after that updates real time database with basic user informations under 'Users' node
     * @param credential Credential generated after putting verification ID
     */
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                signUpListener.onSuccess(FirebaseRefKeys.SIGN_IN)
            } else {
                signUpListener.onFailure(FirebaseRefKeys.SIGN_IN_FAILED)
            }
        }
    }

    /**
     * Go for signIn with entered verification code
     */
    fun verifyOTPAndSignIn(otp: String, listener: SignUpListener) {
        signUpListener = listener
        val credential =
            PhoneAuthProvider.getCredential(verificationID!!, otp)
        signInWithCredential(credential)
    }

    /**
     * Sending OTP to phone number entered - a firebase auth service
     */
    fun verifyNumber(number: String, listener: SignUpListener) {
        signUpListener = listener
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            loginCallback()
        )
    }

    /**
     * Callback for OTP verification
     */
    private fun loginCallback(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                val code = p0?.smsCode
                if (code != null){
                    signInWithCredential(p0)
                    signUpListener.onSuccess("Verification success")
                }

            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                signUpListener.onFailure("Verification code sent failed")
            }

            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(p0, p1)
                verificationID = p0
                signUpListener.onSuccess("code successfully sent...")
            }
        }
    }

    interface onAuthListener {
        fun onSuccess(data: Any)
        fun onFailure(error: Any)
    }
}
