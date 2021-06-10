package com.hrudhaykanth116.instagramclone.utils.toasts

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.hrudhaykanth116.instagramclone.R


object ToastHelper {

    // Can be used to cancel existing toast before showing new toast.
    fun show(context: Context, msg: String) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun show(context: Context, @StringRes resId: Int, vararg formatArgs: Any?) {
        val msg = context.getString(resId, *formatArgs)
        show(context, msg)
    }

    fun showSuccessToast(
        context: Context,
        msg: String? = null,
        @StringRes stringResId: Int = -1,
        vararg formatArgs: Any?
    ) {

        var toastMsg: String? = null
        if (msg != null) {
            toastMsg = msg
        } else if (stringResId != -1) {
            toastMsg = context.getString(stringResId, formatArgs)
        }

        if (toastMsg != null) {
            // TODO: 20/4/21 hrudhay check if the drawables need to be dynamic.
            val customToast = getCustomToast(
                context,
                toastMsg,
                R.drawable.bg_toast_green,
                R.drawable.ic_baseline_check_circle_outline_24,
            )
            customToast.show()
        }

    }

    fun showErrorToast(
        context: Context,
        msg: String? = null,
        @StringRes stringResId: Int = -1,
        vararg formatArgs: Any?
    ) {

        var toastMsg: String? = null
        if (msg != null) {
            toastMsg = msg
        } else if (stringResId != -1) {
            toastMsg = context.getString(stringResId, formatArgs)
        }

        val customToast = getCustomToast(
            context,
            toastMsg,
            R.drawable.bg_toast_red,
            R.drawable.ic_baseline_error_24
        )
        customToast.show()
    }

    private fun getCustomToast(
        context: Context,
        toastMsg: String?,
        @DrawableRes bgDrawable: Int,
        @DrawableRes drawableStart: Int,
    ): Toast {
        return Toast(context).also {
            val view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null)
            view.background = ContextCompat.getDrawable(context, bgDrawable)
            val toastText = view.findViewById<AppCompatTextView>(R.id.textview_toast)
            toastText.text = toastMsg
            toastText.setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
            it.view = view
            it.duration = Toast.LENGTH_LONG
        }
    }

}