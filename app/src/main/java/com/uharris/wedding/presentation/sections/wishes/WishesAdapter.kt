package com.uharris.wedding.presentation.sections.wishes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uharris.wedding.R
import com.uharris.wedding.domain.model.Wish
import kotlinx.android.synthetic.main.item_wish.view.*

class WishesAdapter(private var items: MutableList<Wish>, private val listener: (Wish) -> Unit): RecyclerView.Adapter<WishesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wish, parent, false)
        return WishesAdapter.ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    fun setItems(wishes: MutableList<Wish>) {
        this.items = wishes
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Wish, listener: (Wish) -> Unit) = with(itemView) {
            nameTextView.text = "${item.user.firstName} ${item.user.lastName}"
            commentTextView.text = item.comment
            setOnClickListener { listener(item) }
        }
    }
}