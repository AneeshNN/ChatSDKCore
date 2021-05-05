package com.example.chatsdkcore.auth

/**
 * Created by Aneesh NN on 3/24/21.
 */
import androidx.lifecycle.MutableLiveData
import com.example.chatsdkcore.constants.FirebaseRefKeys
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

/**
 * ViewModel for home activity - has functionality as sign in with verification - user can enter phone number and after verification, can get registered
 * Here, entering phone number and entering verification code are handled on the same page
 */
open class AddUserViewModel : BaseViewModel() {
    private var verificationID: String? = null
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
    fun login() {
        if (buttonText.value == FirebaseRefKeys.VERIFY) {
            verifyNumberWithCode()
        } else {
            val phoneNumber = editTextValue.value!!.trim()
            if (phoneNumber.length < 10) {
                toastMsg.value = FirebaseRefKeys.ENTER_VALID_NUMBER
                return
            }
            verifyNumber()
        }
    }

    /**
     * If signIn task is success, creates a user in firebase auth section with the phone number entered,
     * and after that updates real time database with basic user informations under 'Users' node
     * @param credential Credential generated after putting verification ID
     */
    private fun signInWithCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
//                val currentUser = getCurrentUser()
//                val userDB =
//                    DBRepository.instance.getFirebaseReferenceInstance().getDatabaseReference()
//                        .child(KEY_USERS).child(currentUser?.uid!!)
//                userDB.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onCancelled(p0: DatabaseError) {}
//
//                    override fun onDataChange(p0: DataSnapshot) {
//                        val user: HashMap<String, Any> = HashMap()
//                        user[FirebaseRefKeys.KEY_PHONE] = currentUser.phoneNumber!!
//                        user[FirebaseRefKeys.KEY_UID] = AuthRepository.instance.getAuthInstance().uid!!
//                        user[FirebaseRefKeys.KEY_IMAGE] = FirebaseRefKeys.EMPTY_MESSAGE
//                        user[FirebaseRefKeys.KEY_STATUS] = FirebaseRefKeys.DEFAULT_STATUS_MESSAGE
//                        userDB.updateChildren(user)
//                    }
//                })
//                checkUserLogin()
                toastMsg.value = FirebaseRefKeys.SIGN_IN
            } else {
                toastMsg.value = FirebaseRefKeys.VERIFICATION_CODE_WRONG
                stopLoading(FirebaseRefKeys.VERIFY)
            }
        }
    }

    /**
     * Handles visibility of progress bar and makes button text as blank
     */
    private fun loadingView() {
        progressbarVisibility.value = 0
        buttonText.value = FirebaseRefKeys.EMPTY_MESSAGE
    }

    /**
     * Handles visibility of progress bar and makes button text as blank
     * @param text button text
     */
    private fun stopLoading(text: String) {
        progressbarVisibility.value = 8
        buttonText.value = text
    }

    /**
     * Go for signIn with entered verification code
     */
    private fun verifyNumberWithCode() {
        if (editTextValue.value!!.isEmpty()) {
            toastMsg.value = FirebaseRefKeys.TYPE_VERIFICATION_CODE
            return
        }

        loadingView()
        val credential =
            PhoneAuthProvider.getCredential(verificationID!!, editTextValue.value.toString())
        signInWithCredential(credential)
    }

    /**
     * Sending OTP to phone number entered - a firebase auth service
     */
    private fun verifyNumber() {
        loadingView()
        buttonText.value = FirebaseRefKeys.EMPTY_MESSAGE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            editTextValue.value.toString(),
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
                editTextHint.value = FirebaseRefKeys.ENTER_PHONE_NUMBER
                stopLoading(FirebaseRefKeys.SUBMIT)
            }

            override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(p0, p1)
                verificationID = p0
                editTextValue.value = FirebaseRefKeys.EMPTY_MESSAGE
                editTextHint.value = FirebaseRefKeys.ENTER_VERIFICATION_CODE
                stopLoading(FirebaseRefKeys.VERIFY)
            }
        }
    }
}