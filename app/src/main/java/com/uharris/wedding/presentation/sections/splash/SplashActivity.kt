package com.uharris.wedding.presentation.sections.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.uharris.wedding.R
import com.uharris.wedding.presentation.sections.main.MainActivity
import com.uharris.wedding.presentation.sections.register.RegisterActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject lateinit var id: String
    private val handler = Handler()

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            if (id.isEmpty()) {
                RegisterActivity.startActivity(this)
                finish()
            } else {
                MainActivity.startActivity(this)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AndroidInjection.inject(this)

        handler.postDelayed(mRunnable, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(mRunnable)
    }
}
