package com.uharris.wedding.presentation.sections.gift


import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.uharris.wedding.R
import kotlinx.android.synthetic.main.fragment_gift.*


/**
 * A simple [Fragment] subclass.
 *
 */
class GiftFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gift, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val archerBoldFont = Typeface.createFromAsset(
            context?.assets,
            "fonts/ArcherPro-Bold.otf")
        val archerThinFont = Typeface.createFromAsset(
            context?.assets,
            "fonts/ArcherPro-Medium.otf")

        accountFacebank.typeface = archerBoldFont
        accountNumberFacebank.typeface = archerThinFont
        accountNameFacebank.typeface = archerThinFont
        accountAbaFacebank.typeface = archerThinFont
        accountAddressFacebank.typeface = archerThinFont
        accountSwiftFacebank.typeface = archerThinFont

        zelle.typeface = archerBoldFont
        nameZelle.typeface = archerThinFont
        emailZelle.typeface = archerThinFont

        accountMercantil.typeface = archerBoldFont
        accountNumberMercantil.typeface = archerThinFont
        accountEmailMercantil.typeface = archerThinFont
        accountIdMercantil.typeface = archerThinFont
        accountNameMercantil.typeface = archerThinFont
    }
}
