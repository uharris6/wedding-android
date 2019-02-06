package com.uharris.wedding.presentation.sections.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Photo
import kotlinx.android.synthetic.main.item_photos.view.*

class PhotosAdapter(private var items: MutableList<Photo>, private val listener: (Photo) -> Unit) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photos, parent, false)
        val height = parent.measuredWidth / 3
        view.minimumWidth = height
        return PhotosAdapter.ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    fun setItems(photos: MutableList<Photo>) {
        items = photos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Photo, listener: (Photo) -> Unit) = with(itemView) {
            Picasso.get().load(item.url).into(photoImageView)
            setOnClickListener { listener(item) }
        }
    }
}