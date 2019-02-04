package com.uharris.wedding.presentation.sections.sites

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

    val HEADER_VIEW_TYPE = 0
    val LOCATION_VIEW_TYPE = 1
    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == HEADER_VIEW_TYPE) {
             SitesAdapter.HeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
        } else {
            return SitesAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sites, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER_VIEW_TYPE
        } else if (position == 2) {
            HEADER_VIEW_TYPE
        } else {
            LOCATION_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        if(items.size == 0)
            return 0
        return items.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        if (holder is SitesAdapter.HeaderHolder) {
            if(position == 0) {
                holder.bind("Ceremonia")
            } else {
                holder.bind("Recepción")
            }
            count += 1
        } else {
            (holder as ViewHolder).bind(items[position - count], listener)
        }
    }

    fun setItems(items: MutableList<Site>) {
        this.items = items
        count = 0
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Site, listener: (Site) -> Unit) = with(itemView) {
            Picasso.get().load(item.picture).into(siteImage)
            siteTitle.text = item.name
            siteAddress.text = item.address
            setOnClickListener { listener(item) }
        }
    }

    class HeaderHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(text: String) = with(itemView) {
            headerTitle.text = text
        }
    }
}