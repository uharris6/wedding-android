package com.uharris.wedding.presentation.sections.sites.detail

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Site

import kotlinx.android.synthetic.main.activity_site_detail.*
import kotlinx.android.synthetic.main.content_site_detail.*

class SiteDetailActivity : AppCompatActivity() {

    private lateinit var site: Site
    private val MAP_URL = "http://maps.google.com/maps/api/staticmap?sensor=false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }

        val archerBoldFont = Typeface.createFromAsset(assets,
            "fonts/ArcherPro-Bold.otf")
        val archerThinFont = Typeface.createFromAsset(assets,
            "fonts/ArcherPro-Medium.otf")

        site = intent.getParcelableExtra(SITE_EXTRA)

        Picasso.get().load(site.picture).into(siteImage)

        collapsingToolbarLayout.title = site.name
        collapsingToolbarLayout.setExpandedTitleTypeface(archerBoldFont)
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingToolbarLayout.setCollapsedTitleTypeface(archerBoldFont)
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        siteDescription.typeface = archerThinFont
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            siteDescription.text = Html.fromHtml(site.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            siteDescription.text = Html.fromHtml(site.description)
        }

        siteAddress.text = site.address
        siteAddress.typeface = archerThinFont

        siteEmailText.typeface = archerBoldFont
        val email = String.format(getString(R.string.site_email),site.email)
        siteEmailText.text = email

        sitePhoneText.typeface = archerBoldFont
        sitePhoneText.text = "Teléfono: ${site.phone}"

        siteWebText.typeface = archerBoldFont
        siteWeb.setOnClickListener {
            if (site.url != "") {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(site.url)
                startActivity(i)
            } else {
                Toast.makeText(this, "El sitio no posee página web.", Toast.LENGTH_LONG).show()
            }
        }

        val mapUrl =
            MAP_URL + "&key=AIzaSyA_C694olwHBRnGJ7ZaAvgZXKoxftPdHCQ&center=" + site.latitude + "," + site.longitude +
                    "&zoom=16&size=600x300&scale=2&sensor=false&markers=color:red%7C" +
                    site.latitude + "," + site.longitude

        Picasso.get()
            .load(mapUrl)
            .into(siteMap)

        siteGpsText.typeface = archerBoldFont
        siteGps.setOnClickListener {
            val gpsIntentUri = Uri.parse(
                "google.navigation:q=${site.latitude},${site.longitude}&mode=d"
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gpsIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        siteEmail.setOnClickListener {
            if (site.email != "") {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "message/rfc822"
                i.putExtra(Intent.EXTRA_EMAIL, arrayOf(site.email))
                i.putExtra(Intent.EXTRA_SUBJECT, "")
                i.putExtra(Intent.EXTRA_TEXT, "")
                try {
                    startActivity(Intent.createChooser(i, "Enviar correo electrónico"))
                } catch (ex: android.content.ActivityNotFoundException) {
                    Toast.makeText(
                        this@SiteDetailActivity,
                        "No existen clientes de correo electrónicos instaldos", Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(this, "El sitio no posee correo eletrónico.", Toast.LENGTH_LONG).show()
            }
        }

        sitePhone.setOnClickListener {
            if(site.phone != "") {
                phoneCallDialog()
            } else {
                Toast.makeText(this, "El sitio no posee teléfono.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun phoneCallDialog() {
        AlertDialog.Builder(this)
            .setMessage(site.phone)
            .setPositiveButton("LLAMAR", DialogInterface.OnClickListener { dialog, which ->
                // continue with delete
                if (Build.VERSION.SDK_INT >= 23) {
                    val hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CALL_PHONE)
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            showMessageOKCancel("Ud. debe dar permisos para hacer llamadas.",
                                DialogInterface.OnClickListener { dialog, which ->
                                    requestPermissions(
                                        arrayOf(Manifest.permission.CALL_PHONE),
                                        REQUEST_CODE_ASK_PERMISSIONS
                                    )
                                })
                            return@OnClickListener
                        }
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_CONTACTS),
                            REQUEST_CODE_ASK_PERMISSIONS
                        )
                        return@OnClickListener
                    }
                    phoneCall()
                } else {
                    phoneCall()
                }
            })
            .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                // do nothing
            })
            .show()
    }

    fun phoneCall() {
        val phone = "tel:" + site.phone
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse(phone)
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                phoneCall()
            } else {
                // Permission Denied
                Toast.makeText(this, "Negado permiso para realizar llamadas", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    companion object {

        const val REQUEST_CODE_ASK_PERMISSIONS = 123
        const val SITE_EXTRA = "site_extra"

        fun startActivity(activity: Activity, site: Site) {
            val intent = Intent(activity, SiteDetailActivity::class.java).putExtra(SITE_EXTRA, site)
            activity.startActivity(intent)
        }
    }

}
