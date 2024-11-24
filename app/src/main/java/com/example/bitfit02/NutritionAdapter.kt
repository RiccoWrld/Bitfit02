package com.example.bitfit02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


const val NUTRITION_EXTRA = "NUTRITION_EXTRA"
private const val TAG = "FoodAdapter"

class NutritionAdapter (private val context: Context, private val foods: List<Nutrition>) :
    RecyclerView.Adapter<NutritionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val foodTV = itemView.findViewById<TextView>(R.id.food_info)
        private val calorieTV = itemView.findViewById<TextView>(R.id.cal_val)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(food: Nutrition) {
            foodTV.text = food.food
            calorieTV.text = food.calories
        }

        override fun onClick(v: View?) {
            //No Operation
        }
    }
}

