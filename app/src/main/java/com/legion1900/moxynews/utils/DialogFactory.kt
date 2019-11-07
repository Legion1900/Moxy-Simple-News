package com.legion1900.moxynews.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

object DialogFactory {
    fun buildErrorDialog(msg: Int, btnText: Int, callback: () -> Unit): DialogFragment {
        return object : DialogFragment() {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val builder = AlertDialog.Builder(context)
                builder.setMessage(msg)
                    .setPositiveButton(btnText
                    ) { dialog, which ->
                        callback()
                    }
                return builder.create()
            }
        }
    }
}