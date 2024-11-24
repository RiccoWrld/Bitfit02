package com.example.bitfit02

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFrag (application: Application) : Fragment() {

    private lateinit var avg_cal_val: TextView
    private lateinit var max_cal_val: TextView
    private lateinit var min_cal_val: TextView
    private lateinit var clearDataBTN: Button
    private lateinit var application: Application

    init {
        this@DashboardFrag.application = application
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_dashboard, container, false)

        avg_cal_val = view.findViewById(R.id.avg_cal_val)
        max_cal_val = view.findViewById(R.id.max_cal_val)
        min_cal_val = view.findViewById(R.id.min_cal_val)
        clearDataBTN = view.findViewById(R.id.clear_dataBtn)

        clearDataBTN.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (application as NutritionApplication).db.nutritionDao().deleteAll()
            }
            reset()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            (application as NutritionApplication).db.nutritionDao().getAll().collect { databaseList ->
                databaseList.map { entity  ->
                    Nutrition(
                        entity.food,
                        entity.calories
                    )
                }.also { mappedList ->
                    update(mappedList)
                }
            }
        }
        return view
    }

    fun reset() {
        avg_cal_val.text = "____"
        max_cal_val.text = "____"
        min_cal_val.text = "____"
    }

    fun update(foods: List<Nutrition>) {
        if(foods.isEmpty()) {
            reset()
            return
        }

        var total: Int = 0
        var min: Int = Integer.MAX_VALUE
        var max: Int = Integer.MIN_VALUE
        for (food in foods) {
            val i = Integer.parseInt(food.calories)
            total += i
            if (i < min) {
                min = i
            }
            if(i > max) {
                max = i
            }
        }
        avg_cal_val.text = (total/ foods.size).toString()
        max_cal_val.text = max.toString()
        min_cal_val.text = min.toString()
    }

    companion object {
        fun newInstance(application: Application): DashboardFrag {
            return DashboardFrag(application)
        }
    }
}