package com.uharris.wedding.presentation.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


open class BaseFragment : Fragment() {

    fun showMessage(message: String) {
        activity?.let {
            Snackbar.make(it.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
        }
    }
}