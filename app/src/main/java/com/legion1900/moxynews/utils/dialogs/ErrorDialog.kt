package com.legion1900.moxynews.utils.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.legion1900.moxynews.utils.dialogfactory.ErrorDialogFactory

class ErrorDialog :
    DialogFragment() {

    companion object {
        const val KEY_MSG = "msg"
        const val KEY_TXT = "text"
        const val KEY_CALLBACK = "callback"
    }

    private var msg = 0
    private var btnText = 0
    private lateinit var callback: ErrorDialogFactory.PositiveCallback

    private var isShowing = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        getParams()
        val builder = AlertDialog.Builder(context)
        builder.setMessage(msg)
            .setPositiveButton(
                btnText
            ) { _, _ ->
                isShowing = false
                dismiss()
                callback.onPositive()
            }
        return builder.create()
    }

    private fun getParams() {
        val params = arguments!!
        msg = params.getInt(KEY_MSG)
        btnText = params.getInt(KEY_TXT)
        callback = params.getParcelable(KEY_CALLBACK)!!
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isShowing) {
            isShowing = true
            super.show(manager, tag)
        }
    }
}