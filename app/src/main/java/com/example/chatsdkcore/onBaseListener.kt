package com.example.chatsdkcore

/**
 * Created by Aneesh NN on 3/24/21.
 */

import androidx.fragment.app.DialogFragment

/**
 * Interface for manage some basic functionality along with an activity - use case come with base activity
 */
interface onBaseListener {
    /**
     * Shows toast
     * @param msg The message for the toast to show
     */
    fun showToast(toastMessage: String)


}