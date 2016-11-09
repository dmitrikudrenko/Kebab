package io.github.dmitrikudrenko.kebab.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment

class ProgressDialogFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = ProgressDialog(activity)
        dialog.isIndeterminate = true
        dialog.setCancelable(false)
        return dialog
    }

    companion object {
        val TAG = "ProgressDialogFragment"

        fun create(): ProgressDialogFragment {
            val frag = ProgressDialogFragment()
            frag.isCancelable = false
            return frag
        }
    }

}
