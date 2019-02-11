package com.uharris.wedding.presentation.sections.wishes.detail


import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Wish
import kotlinx.android.synthetic.main.fragment_detail_wish.*

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailWishFragment : DialogFragment() {

    companion object {

        const val WISH_EXTRA = "wish"

        fun newInstance(wish: Wish): DetailWishFragment {

            val bundle = Bundle()
            bundle.putParcelable(WISH_EXTRA, wish)

            val fragment = DetailWishFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_wish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wish = arguments?.getParcelable<Wish>(WISH_EXTRA)

        val archerBoldFont = Typeface.createFromAsset(context?.assets,
            "fonts/ArcherPro-Bold.otf")
        val archerThinFont = Typeface.createFromAsset(context?.assets,
            "fonts/ArcherPro-Medium.otf")

        wishTextView.typeface = archerThinFont
        wishTextView.text = wish?.comment
        nameTextView.typeface = archerBoldFont
        nameTextView.text = "${wish?.user?.firstName} ${wish?.user?.lastName}"
    }

}
