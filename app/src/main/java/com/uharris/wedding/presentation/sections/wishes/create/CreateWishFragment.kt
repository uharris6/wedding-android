package com.uharris.wedding.presentation.sections.wishes.create


import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment

import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Wish
import com.uharris.wedding.presentation.sections.wishes.WishesFragment
import kotlinx.android.synthetic.main.fragment_create_wish.*

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateWishFragment : DialogFragment() {

    interface CreateWishListener {
        fun getWish(wish: String)
    }

    var callback: CreateWishListener? = null

    companion object {
        const val WISH_EXTRA = "wish"

        fun newInstance(): CreateWishFragment {
            val fragment = CreateWishFragment()
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = parentFragment as CreateWishListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_wish, container, false)
    }

    private lateinit var wish: Wish

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val archerThinFont = Typeface.createFromAsset(context?.assets,
            "fonts/ArcherPro-Medium.otf")
        wishEditText.typeface = archerThinFont

        sendButton.setOnClickListener {
            if(wishEditText.text.toString().trim().isNullOrBlank()) {
                Toast.makeText(context, "Por favor, escriba un deseo para los novios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            callback?.getWish(wishEditText.text.toString().trim())
        }
    }

}
