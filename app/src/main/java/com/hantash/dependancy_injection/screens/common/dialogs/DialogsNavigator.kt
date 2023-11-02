package com.hantash.dependancy_injection.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogsNavigator(private val fragmentManager: FragmentManager) {

    fun showServerError() {
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

}