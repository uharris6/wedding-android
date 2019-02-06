package com.uharris.wedding.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.uharris.wedding.R

class MediaUtils(val activity: Activity) {

    val REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 123
    val REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL = 124

    private var mCurrentPhotoPath: String = ""
    val DATE_FORMAT = "yyyyMMdd_HHmmss"

    fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity)
        pictureDialog.setTitle("Seleccioné una opción")
        val pictureDialogItems = arrayOf("Galería", "Cámara")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> validateExternalStoragePermission()
            }
        }
        pictureDialog.show()
    }

    fun showPictureDialogCamera() {
        val pictureDialog = AlertDialog.Builder(activity)
        pictureDialog.setTitle("Seleccioné una opción")
        val pictureDialogItems = arrayOf("Cámara")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> validateExternalStoragePermission()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.startActivityForResult(galleryIntent, GALLERY)
    }

    private fun validateExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (!activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("Ud. debe dar permisos de escritura utilizar la cámara.",
                        DialogInterface.OnClickListener { dialog, which ->
                            activity.requestPermissions(
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL
                            )
                        })
                    return
                }
                activity.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_CONTACTS),
                    REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL
                )
                return
            }
            takePhotoFromCamera()
        } else {
            takePhotoFromCamera()
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    fun takeCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        activity,
                        "com.uharris.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity.startActivityForResult(takePictureIntent, CAMERA)
                }
            }
        }
    }

    private fun takePhotoFromCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            val hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.CAMERA)
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (!activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showMessageOKCancel("Ud. debe dar permisos para utilizar la cámara.",
                        DialogInterface.OnClickListener { dialog, which ->
                            activity.requestPermissions(
                                arrayOf(Manifest.permission.CAMERA),
                                REQUEST_CODE_ASK_PERMISSIONS_CAMERA
                            )
                        })
                    return
                }
                activity.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_CONTACTS),
                    REQUEST_CODE_ASK_PERMISSIONS_CAMERA
                )
                return
            }
            takeCamera()
        } else {
            takeCamera()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS_CAMERA -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                takeCamera()
            } else {
                // Permission Denied
                Toast.makeText(activity, "Negado permiso para utilizar la cámara", Toast.LENGTH_SHORT)
                    .show()
            }
            REQUEST_CODE_ASK_PERMISSIONS_EXTERNAL -> if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera()
            } else {
                Toast.makeText(activity, "Negado permiso para utilizar la cámara", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun getPath(): String {
        return mCurrentPhotoPath
    }

    @Throws(IOException::class)
    fun createImageFile(galleryFolder: File): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "image_" + timeStamp + "_"
        return File.createTempFile(imageFileName, ".jpg", galleryFolder)
    }

    fun saveImage(myBitmap: Bitmap): File? {
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val galleryFolder = File(storageDirectory, activity.getString(R.string.app_name))
        if (!galleryFolder.exists()) {
            val wasCreated = galleryFolder.mkdirs()
            if (!wasCreated) {
                Log.e("CapturedImages", "Failed to create directory")
            }
        }
        var outputPhoto: FileOutputStream? = null
        val file = createImageFile(galleryFolder)
        try {

            outputPhoto = FileOutputStream(file)
            myBitmap
                    .compress(Bitmap.CompressFormat.JPEG, 75, outputPhoto)

            return file
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return null

    }

    fun decodeFile(file: File): File? {
        var bitmap: Bitmap? = null

        //Decode image size
        var o = BitmapFactory.Options()
        o.inJustDecodeBounds = true


        var fis: FileInputStream?
        try {
            fis = FileInputStream(file)
            BitmapFactory.decodeStream(fis, null, o)
            fis.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        val IMAGE_MAX_SIZE = 1024
        var scale: Int = 1
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = Math.pow(2.0, Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    Math.max(o.outHeight, o.outWidth).toDouble()) / Math.log(0.5))).toInt()
        }

        //Decode with inSampleSize
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        try {
            fis = FileInputStream(file)
            bitmap = BitmapFactory.decodeStream(fis, null, o2)
            fis.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 75, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    companion object {
        const val GALLERY: Int = 1000
        const val CAMERA: Int = 1001
    }
}