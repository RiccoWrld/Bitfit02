package com.example.bitfit02

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFrag(application: Application) : Fragment() {

    // Remove redundant 'application' declaration as it's already passed in the constructor
    private lateinit var avg_cal_val: TextView
    private lateinit var max_cal_val: TextView
    private lateinit var min_cal_val: TextView
    private lateinit var clearDataBTN: Button
    private val application: Application = application

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_dashboard, container, false)

        // Initialize UI components
        avg_cal_val = view.findViewById(R.id.avg_cal_val)
        max_cal_val = view.findViewById(R.id.max_cal_val)
        min_cal_val = view.findViewById(R.id.min_cal_val)
        clearDataBTN = view.findViewById(R.id.clear_dataBtn)

        // Clear data when the button is clicked
        clearDataBTN.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (application as NutritionApplication).db.nutritionDao().deleteAll()
            }
            reset()
        }

        // Fetch data from the database in the background thread
        lifecycleScope.launch(Dispatchers.IO) {
            (application as NutritionApplication).db.nutritionDao().getAll().collect { databaseList ->
                val mappedList = databaseList.map { entity ->
                    Nutrition(entity.food, entity.calories)
                }

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    update(mappedList)
                }
            }
        }

        return view
    }

    // Reset the displayed values
    private fun reset() {
        avg_cal_val.text = "____"
        max_cal_val.text = "____"
        min_cal_val.text = "____"
    }

    // Update the UI with the latest data
    fun update(foods: List<Nutrition>) {
        if (foods.isEmpty()) {
            reset()
            return
        }

        var total: Int = 0
        var min: Int = Integer.MAX_VALUE
        var max: Int = Integer.MIN_VALUE

        // Loop through the foods and calculate the total, min, and max values
        for (food in foods) {
            try {
                val i = Integer.parseInt(food.calories)
                total += i
                if (i < min) {
                    min = i
                }
                if (i > max) {
                    max = i
                }
            } catch (e: NumberFormatException) {
                Log.e("DashboardFrag", "Error parsing calories for food: ${food.calories}. Skipping this item.")
            }
        }

        // Update UI with calculated values (on the main thread)
        avg_cal_val.text = (total / foods.size).toString()
        max_cal_val.text = max.toString()
        min_cal_val.text = min.toString()
    }

    companion object {
        fun newInstance(application: Application): DashboardFrag {
            return DashboardFrag(application)
        }
    }
}
