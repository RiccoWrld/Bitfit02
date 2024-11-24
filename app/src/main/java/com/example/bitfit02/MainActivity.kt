package com.example.bitfit02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bitfit02.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentManager: FragmentManager = supportFragmentManager

        val dashboardFrag: DashboardFrag = DashboardFrag.newInstance(application)
        val NutritionListFragment: NutritionListFragment = NutritionListFragment.newInstance(application)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.dashboard -> fragment = dashboardFrag
                R.id.logs -> fragment = NutritionListFragment
            }
            fragmentManager.beginTransaction().replace(R.id.main, fragment).commit()
            true
        }

        bottomNavigationView.selectedItemId = R.id.logs

        val recordFoodBtn = findViewById<Button>(R.id.recordFoodBtn)
        recordFoodBtn.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            this.startActivity(intent)
            dashboardFrag.update(NutritionListFragment.foods)
        }

    }
}