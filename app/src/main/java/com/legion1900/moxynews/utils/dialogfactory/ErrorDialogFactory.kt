package com.legion1900.moxynews.utils.dialogfactory

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.legion1900.moxynews.utils.dialogs.ErrorDialog

object ErrorDialogFactory {

    fun createErrorDialog(
        @StringRes msg: Int,
        @StringRes btnText: Int,
        callback: PositiveCallback
    ): DialogFragment {
        val dialogFragment = ErrorDialog()
        val args = Bundle()
        ErrorDialog.run {
            args.putInt(KEY_MSG, msg)
            args.putInt(KEY_TXT, btnText)
            args.putParcelable(KEY_CALLBACK, callback)
        }
        dialogFragment.arguments = args
        return dialogFragment
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