package com.example.chatsdkcore.auth

/**
 * Created by Aneesh NN on 5/4/21.
 */

import com.google.firebase.auth.FirebaseAuth

/**
 * Class for managing auth processes like phone number / email verification with firebase auth
 */
class AuthRepository {
    private val authInstance = FirebaseAuth.getInstance()

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
}
