package com.uharris.wedding.presentation.sections.sites

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Site
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_sites.view.*

class SitesAdapter(private var items: MutableList<Site>, private val listener: (Site) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sites, parent, false))

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        (holder as ViewHolder).bind(items[position - count], listener)
    }

    fun setItems(items: MutableList<Site>) {
        this.items = items
        count = 0
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Site, listener: (Site) -> Unit) = with(itemView) {
            val archerBoldFont = Typeface.createFromAsset(
                context.assets,
                "fonts/ArcherPro-Bold.otf")
            val archerThinFont = Typeface.createFromAsset(
                context.assets,
                "fonts/ArcherPro-Medium.otf")
            Picasso.get().load(item.picture).into(siteImage)
            siteTitle.typeface = archerBoldFont
            siteTitle.text = item.name
            siteAddress.typeface = archerThinFont
            siteAddress.text = item.address
            setOnClickListener { listener(item) }
        }
    }
}