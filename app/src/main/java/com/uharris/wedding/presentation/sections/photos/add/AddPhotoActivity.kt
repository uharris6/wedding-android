package com.uharris.wedding.presentation.sections.photos.add

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.uharris.wedding.R
import com.uharris.wedding.data.base.Failure
import com.uharris.wedding.domain.model.Photo
import com.uharris.wedding.presentation.base.BaseActivity
import com.uharris.wedding.presentation.base.ViewModelFactory
import com.uharris.wedding.presentation.state.Resource
import com.uharris.wedding.presentation.state.ResourceState
import com.uharris.wedding.utils.MediaUtils
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_add_photo.*
import kotlinx.android.synthetic.main.content_add_photo.*
import java.io.File
import java.io.IOException
import javax.inject.Inject
import com.uharris.wedding.presentation.sections.photos.PhotosFragment


class AddPhotoActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var addPhotoViewModel: AddPhotoViewModel

    lateinit var mediaUtils: MediaUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }

        val archerThinFont = Typeface.createFromAsset(
            assets,
            "fonts/ArcherPro-Medium.otf")

        titleEditText.typeface = archerThinFont

        mediaUtils = MediaUtils(this)

        addPhotoViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddPhotoViewModel::class.java)
        addPhotoViewModel.photoLiveData.observe(this, Observer {
            handleDataState(it)
        })

        addPhotoViewModel.failure.observe(this, Observer {
            handleFailure(it)
        })

        photoImageView.setOnClickListener {
            mediaUtils.showPictureDialog()
        }

        sendButton.setOnClickListener {
            sendButton.isEnabled = false
            if(selectedImageFile == null){
                Toast.makeText(this, "Por favor, seleccioné una foto.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(titleEditText.text.toString().trim().isNullOrBlank()) {
                Toast.makeText(this, "Por favor, coloqué un título a la foto.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            addPhotoViewModel.uploadPhoto(titleEditText.text.toString().trim(), selectedImageFile?.path ?: "")
        }
    }

    private fun handleDataState(resource: Resource<Photo>) {
        when(resource.status){
            ResourceState.SUCCESS -> {
                hideLoader()
                resource.data?.let {
                    val returnIntent = Intent()
                    returnIntent.putExtra(PhotosFragment.PHOTO_EXTRA, it)

                    this.setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                }
            }
            ResourceState.LOADING -> {
                showLoader()
            }
            ResourceState.ERROR -> {
                hideLoader()
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        showMessage(failure.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mediaUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private var selectedImageFile: File? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            return
        }

        if (requestCode == MediaUtils.GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val rotatedBitmap = mediaUtils.modifyOrientation(bitmap, contentURI?.toString() ?: "")
//                    photoImageView.setImageBitmap(rotatedBitmap)
                    Picasso.get().load(contentURI).into(photoImageView)
                    val file = mediaUtils.saveImage(rotatedBitmap)
                    selectedImageFile = file
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }else if (requestCode == MediaUtils.CAMERA) {
            val file = File(mediaUtils.getPath())
            val newFile = mediaUtils.decodeFile(file)

            newFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.uharris.fileprovider",
                    it
                )
                Picasso.get().load(photoURI).into(photoImageView)
                selectedImageFile = it
            }
        }
    }

    companion object {

        fun startActivity(a: Activity){
            a.startActivity(Intent(a, AddPhotoActivity::class.java))
        }

        fun startActivityForResult(a: Activity, request: Int){
            a.startActivityForResult(Intent(a, AddPhotoActivity::class.java), request)
        }
    }

}
