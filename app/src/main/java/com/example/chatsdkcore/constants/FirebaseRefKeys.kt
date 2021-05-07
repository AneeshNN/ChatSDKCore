package com.example.chatsdkcore.constants

/**
 * Created by Aneesh NN on 3/24/21.
 */

/**
 * Class for managing reference keys in firebase database - contains each node(key)'s name
 */
class FirebaseRefKeys {
    companion object {
        /**
         * Firebase database and storage URLs
         */
        var FIREBASE_DATABASE_ROOT = "https://chatsdk-92ada-default-rtdb.firebaseio.com/"
        var FIREBASE_STORAGE_ROOT = "https://firebasestorage"

        /**
         * Constants
         */
        var EMPTY_MESSAGE = ""
        var ZERO = "0"
        var ONE = "1"

        var GROUP = "group"
        val TYPING = "typing"
        var IMAGE_CHANGED = "Image Changed"
        val VOICE_RECORD = "Voice Record"
        val RECORD_SENT = "Record Sent"
        val ERROR_UPLOAD_IMAGE = "Error with upload the image"
        val DEFAULT_STATUS_MESSAGE = "I am using my WhatsApp :)"
        val SELECT_PICTURE = "Select Picture"
        val IMAGE_SENT = "Image Sent"
        val VERIFY = "Verify"
        val SUBMIT = "submit"
        val ENTER_PHONE_NUMBER = "Enter phone number"
        val ENTER_VERIFICATION_CODE = "Enter verification code"
        val ERROR_OCCURED = "An error occurred, Please try again."
        val ENTER_PHONE = "Enter phone number with country code"
        val ENTER_VALID_NUMBER = "Enter a valid number"
        val TYPE_VERIFICATION_CODE = "type verification code!"
        val VERIFICATION_CODE_WRONG = "Verification code is wrong, Please try again."
        val SIGN_IN = "Sign in success"
        val SIGN_IN_FAILED = "Sign in failed"

        /**
         * Firebase database KEYs
         */
        val KEY_USERS = "Users"
        val KEY_CHAT_ID = "ChatID"
        val KEY_CHAT_TYPE = "chatType"
        val KEY_USER_NAME = "userName"
        val KEY_USER_UID = "userUid"
        val KEY_MOOD = "mood"
        val KEY_ONLINE = "online"
        val KEY_PHONE = "Phone"
        val KEY_UID = "Uid"
        val KEY_IMAGE = "Image"
        val KEY_STATUS = "Status"
        val KEY_CHATS = "Chats"
        val KEY_PHOTO = "Photo"
        val KEY_INFO = "Info"
        val KEY_CHAT = "chat"
        val KEY_DIRECT = "direct"
        val KEY_MESSAGES = "messages"
        val KEY_UNREAD_MESSAGE = "unreadMessage"
        val KEY_MEDIA_TYPE = "mediaType"
        val KEY_LAST_SENDER = "lastSender"
        val KEY_LAST_MESSAGE = "lastMessage"
        val KEY_LAST_MESSAGE_DATE = "lastMessageDate"
        val KEY_MESSAGE = "message"
        val KEY_HIDE = "hide"
        val KEY_INSTANCE = "instance"
        val KEY_USERS_PHONE = "usersPhone"
        val KEY_USERS_NAMES = "usersNames"
        val KEY_USERS_IMAGE = "usersImage"
        val KEY_USER_IMAGE = "userImage"

    }
}