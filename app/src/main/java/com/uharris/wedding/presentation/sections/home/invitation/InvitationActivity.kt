package com.uharris.wedding.presentation.sections.home.invitation

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.uharris.wedding.R

import kotlinx.android.synthetic.main.activity_invitation.*
import kotlinx.android.synthetic.main.content_invitation.*

class InvitationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitation)
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }

        val archerBoldFont = Typeface.createFromAsset(
            this.assets,
            "fonts/ArcherPro-Bold.otf")
        titleTextView.typeface = archerBoldFont
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        fun startActivitiy(a: Activity) {
            a.startActivity(Intent(a, InvitationActivity::class.java))
        }
    }
}
