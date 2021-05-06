package com.example.chatsdkcore

/**
 * Created by Aneesh NN on 3/24/21.
 */


/**
 * Interface for manage some basic functionality along with an activity - use case come with base activity
 */
interface SignUpListener {
    /**
     * Shows toast
     * @param msg The message for the toast to show
     */
    fun onSuccess(data: Any)
    fun onFailure(error: Any)
}