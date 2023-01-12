package com.uharris.wedding.presentation.sections.register

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.User
import com.uharris.wedding.presentation.base.BaseActivity
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.sections.main.MainActivity
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AndroidInjection.inject(this)

        registerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)

        registerViewModel.liveData.observe(this, Observer {
            it?.let {
                handleDataState(it)
            }
        })

        val archerBoldFont = Typeface.createFromAsset(assets,
            "fonts/ArcherPro-Bold.otf")
        val archerThinFont = Typeface.createFromAsset(assets,
            "fonts/ArcherPro-Medium.otf")
        titleTextView.typeface = archerBoldFont
        nameEditText.typeface = archerThinFont
        lastNameEditText.typeface = archerThinFont
        codeEditText.typeface = archerThinFont

        logInButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val code = codeEditText.text.toString().trim()

            if(!code.isNullOrBlank() && code.toLowerCase() == "230219"){
                if(name.isNullOrBlank() && lastName.isNullOrBlank()) {
                    Toast.makeText(this, "El código ingresado es incorrecto.", Toast.LENGTH_SHORT).show()
                } else {
                    registerViewModel.attempRegisterUser(name, "", lastName)
                }
            }else {
                Toast.makeText(this, "El código ingresado es incorrecto.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleDataState(resource: Resource<User>) {
        when(resource.status) {
            ResourceState.LOADING -> {
                showLoader()
            }

            ResourceState.SUCCESS -> {
                hideLoader()
                resource.data?.let {
                    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                    val editor = prefs.edit()
                    editor.putString("id", it.id)
                    editor.putString("name", it.firstName)
                    editor.putString("lastname", it.lastName)
                    editor.apply()

                    MainActivity.startActivity(this)
                    finish()
                }
            }

            ResourceState.ERROR -> {
                hideLoader()
                Toast.makeText(this, "Hubo error en el servidor. Intentelo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        fun startActivity(a: Activity) {
            a.startActivity(Intent(a, RegisterActivity::class.java))
        }
    }
}
