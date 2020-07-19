package com.example.testandroid.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testandroid.R
import com.example.testandroid.model.Asset
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(private val assets: ArrayList<Asset>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.recyclerview_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = assets.get(position)
        holder.bind(item)
    }

    fun addAssets(users: List<Asset>) {
        this.assets.apply {
            clear()
            addAll(users)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(asset: Asset) {
            itemView.apply {
                name.text = asset.user!!.username
                description.text = asset.description
                Glide.with(profile.context)
                    .load(asset.user!!.profilePicture)
                    .circleCrop()
                    .into(profile)
                Glide.with(image.context)
                    .load(asset.previewImg)
                    .into(image)
            }
        }
    }

}
