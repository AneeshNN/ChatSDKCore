package com.example.chatsdkcore.auth

/**
 * Created by Aneesh NN on 3/24/21.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

/**
 * BaseViewModel for all the view models in the application, has some basic functionality implementation like listening
 * for toast messages and also checks the user is logged in or not
 */
open class BaseViewModel : ViewModel() {
    var isUserIsLoggedIn = MutableLiveData<Boolean>()
    var toastMsg = MutableLiveData<String>()

    /**
     * Listen for toast messages
     */
    fun toastMessagesLiveData(): LiveData<String> = toastMsg

    /**
     * Checks the user is logged in or not
     */
    fun checkUserLogin(): LiveData<Boolean> {
        isUserIsLoggedIn.value = AuthRepository.instance.getAuthInstance().currentUser != null
        return isUserIsLoggedIn
    }
}