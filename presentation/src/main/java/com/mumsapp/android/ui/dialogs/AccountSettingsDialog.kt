package com.mumsapp.android.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import butterknife.ButterKnife
import com.mumsapp.android.R

class AccountSettingsDialog(context: Context) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(context)
    }

    private fun setup(context: Context) {
        setContentView(R.layout.dialog_account_settings)
        ButterKnife.bind(this)
    }
}