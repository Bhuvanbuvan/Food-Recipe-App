package com.example.foodrecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.databinding.FooditemBinding
import com.example.foodrecipe.model.HotelList
import com.squareup.picasso.Picasso

class FoodListAdapter( var list:List<HotelList>):RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding:FooditemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FooditemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem=list[position]

        holder.binding.foodName.text=currentItem.foodName.toString()
        holder.binding.price.text=currentItem.foodPrice.toString()
        holder.binding.desc.text=currentItem.disc.toString()

        Picasso.get().load(currentItem.image).into(holder.binding.foodImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}