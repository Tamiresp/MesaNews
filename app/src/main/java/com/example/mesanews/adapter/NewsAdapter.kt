package com.example.mesanews.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mesanews.R
import com.example.mesanews.data.entity.News
import com.example.mesanews.feature.detail.DetailActivity
import com.example.mesanews.util.Constants.URL
import kotlinx.android.synthetic.main.item.view.*

class NewsAdapter(private val items: MutableList<News>, context: Context): RecyclerView.Adapter<MyViewHolder>() {
    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: News = items[position]
        holder.textTitle.text = item.title
        holder.textDesc.text = item.description
        Glide.with(holder.itemView.context)
            .load(item.image_url)
            .into(holder.imageNews)
        holder.card.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(URL, item.url)
            context.startActivity(intent)
        }
    }

    fun addItem(item: News){
        items.add(item)
        notifyItemInserted(itemCount)
    }
}

class MyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val imageNews: ImageView = itemView.findViewById(R.id.img_news)
    val textTitle: TextView = itemView.findViewById(R.id.tv_title)
    val textDesc: TextView = itemView.findViewById(R.id.tv_desc)
    val card: CardView = itemView.findViewById(R.id.cardView)
}