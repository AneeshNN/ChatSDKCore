package com.example.chatsdkcore.auth

/**
 * Created by Aneesh NN on 3/24/21.
 */
import androidx.lifecycle.MutableLiveData
import com.example.chatsdkcore.SignUpListener
import com.example.chatsdkcore.constants.FirebaseRefKeys
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

/**
 * ViewModel for home activity - has functionality as sign in with verification - user can enter phone number and after verification, can get registered
 * Here, entering phone number and entering verification code are handled on the same page
 */
open class AddUserViewModel : BaseViewModel() {
    private var verificationID: String? = null
    private lateinit var signUpListener: SignUpListener

    /**
     * If signIn task is success, creates a user in firebase auth section with the phone number entered,
     * and after that updates real time database with basic user informations under 'Users' node
     * @param credential Credential generated after putting verification ID
     */
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

            } else {

            }
        }
    }

    /**
     * Go for signIn with entered verification code
     */
    fun verifyOTP(otp: String) {
        if (otp.isEmpty()) {
            toastMsg.value = FirebaseRefKeys.TYPE_VERIFICATION_CODE
            return
        }
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
                if (code != null)
                    signInWithCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                toastMsg.value = FirebaseRefKeys.ERROR_OCCURED
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