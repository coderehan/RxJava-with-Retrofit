package com.cipl.rxjavawithretrofit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cipl.rxjavawithretrofit.databinding.AdapterFoodItemBinding
import com.cipl.rxjavawithretrofit.models.FoodResponse

class MyFoodItemAdapter(
    private val context: Context,
    private var foodList: ArrayList<FoodResponse>
) : RecyclerView.Adapter<MyFoodItemAdapter.MyViewHolder>() {

    // This function will basically send data to this adapter which is present in Observables of RxJava
    fun setData(foodList: List<FoodResponse>) {
        this.foodList = foodList as ArrayList<FoodResponse>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            AdapterFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val food = foodList[position]
        val binding = holder.binding

        binding.tvName.text = food.name
        binding.tvPrice.text = food.price.toString()
        Glide.with(context).load(food.image).into(binding.ivFood)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class MyViewHolder(val binding: AdapterFoodItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}