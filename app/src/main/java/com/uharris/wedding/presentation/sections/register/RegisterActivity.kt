package com.uharris.wedding.presentation.sections.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.uharris.wedding.R
import com.uharris.wedding.presentation.base.BaseActivity
import com.uharris.wedding.presentation.base.ViewModelFactory
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)

        registerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
    }

}
