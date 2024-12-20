package com.example.bitfit02

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "NutritionListFragment"

class NutritionListFragment (application: Application) : Fragment() {

    val foods = mutableListOf<Nutrition>()
    private lateinit var nutritionRV: RecyclerView
    private lateinit var nutritionAdapter: NutritionAdapter
    private lateinit var application: Application

    init {
        this@NutritionListFragment.application = application
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frag_food_list, container, false)

        val layoutManger = LinearLayoutManager(context)
        nutritionRV = view.findViewById(R.id.foodRV)
        nutritionRV.layoutManager = layoutManger
        nutritionRV.setHasFixedSize(true)
        nutritionAdapter = NutritionAdapter(view.context, foods)
        nutritionRV.adapter = nutritionAdapter

        lifecycleScope.launch {
            try {
                (application as NutritionApplication).db.nutritionDao().getAll().collect { databaseList ->
                    val mappedList = databaseList.map { entity ->
                        Nutrition(entity.food, entity.calories)
                    }

                    withContext(Dispatchers.Main) {
                        foods.clear()
                        foods.addAll(mappedList)
                        nutritionAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data from database", e)
            }
        }

        return view
    }

    companion object {
        fun newInstance(application: Application): NutritionListFragment {
            return NutritionListFragment(application)
        }
    }
}