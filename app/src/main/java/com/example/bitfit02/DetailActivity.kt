package com.example.bitfit02



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val foodInput : EditText = findViewById(R.id.foodInput)
        val caloriesInput: EditText = findViewById(R.id.calorieInput)
        val submitNewFoodBtn : Button = findViewById(R.id.submitBTN)

        submitNewFoodBtn.setOnClickListener {
            lifecycleScope.launch(IO) {
                (application as NutritionApplication).db.nutritionDao().insert(
                    NutritionEntity(
                        food = foodInput.text.toString(),
                        calories = caloriesInput.text.toString()
                    )
                )
            }
            finish()
        }
    }

}