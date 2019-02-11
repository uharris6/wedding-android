package com.uharris.wedding.presentation.sections.photos.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Photo
import kotlinx.android.synthetic.main.activity_detail_photo.*

class DetailPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_photo)

        val photo = intent.getParcelableExtra<Photo>(PHOTO_EXTRA)

        val archerBoldFont = Typeface.createFromAsset(
            assets,
            "fonts/ArcherPro-Bold.otf")

        titleTextView.typeface = archerBoldFont
        titleTextView.text = photo.title
        Picasso.get().load(photo.url).into(photoImageView)

        closeImageButton.setOnClickListener {
            finish()
        }
    }

    companion object {

        const val PHOTO_EXTRA = "photo"

        fun startActivity(a: Activity, photo: Photo) {
            a.startActivity(Intent(a, DetailPhotoActivity::class.java).putExtra(PHOTO_EXTRA, photo))
        }
    }
}
