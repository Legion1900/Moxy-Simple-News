package com.legion1900.moxynews.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.DialogFragment

class ErrorDialog :
    DialogFragment() {

    companion object {
        const val KEY_MSG = "msg"
        const val KEY_TXT = "text"
        const val KEY_CALLBACK = "callback"
    }

    private var msg = 0
    private var btnText = 0
    private lateinit var callback: PositiveCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getParams()
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
            .setPositiveButton(
                btnText
            ) { _, _ ->
                callback.onPositive()
            }
        return builder.create()
    }

    private fun getParams() {
        val params = arguments!!
        msg = params.getInt(KEY_MSG)
        btnText = params.getInt(KEY_TXT)
        callback = params.getParcelable<PositiveCallback>(KEY_CALLBACK)!!
    }

    interface PositiveCallback : Parcelable {
        fun onPositive()
        override fun writeToParcel(dest: Parcel?, flags: Int) {
            /*
            * Nothing to do here.
            * */
        }
        override fun describeContents(): Int = 0
    }
}