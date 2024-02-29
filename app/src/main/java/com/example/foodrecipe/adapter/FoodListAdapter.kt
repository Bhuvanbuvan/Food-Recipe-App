package com.example.foodrecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.databinding.FooditemBinding
import com.example.foodrecipe.model.HotelList
import com.squareup.picasso.Picasso

class FoodListAdapter:RecyclerView.Adapter<FoodListAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding:FooditemBinding):RecyclerView.ViewHolder(binding.root)
    private var differUtilcalback= object:DiffUtil.ItemCallback<HotelList>(){
        override fun areItemsTheSame(oldItem: HotelList, newItem: HotelList): Boolean {
            return oldItem.id.toString()==newItem.id.toString() &&
                    oldItem.hotelName.toString() ==newItem.hotelName.toString()
        }

        override fun areContentsTheSame(oldItem: HotelList, newItem: HotelList): Boolean {
            return oldItem==newItem
        }

    }

    public var differ=AsyncListDiffer(this,differUtilcalback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(FooditemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem=differ.currentList[position]

        holder.binding.foodName.text=currentItem.foodName.toString()
        holder.binding.price.text=currentItem.foodPrice.toString()
        holder.binding.desc.text=currentItem.disc.toString()

        Picasso.get().load(currentItem.image).into(holder.binding.foodImage)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}