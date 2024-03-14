package com.kekulta.pmanager.list.presentation.nav

import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.popBackStackOnBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback {
        findNavController().popBackStack()
    }
}

fun Fragment.exitOnBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback {
        requireActivity().finish()
    }
}