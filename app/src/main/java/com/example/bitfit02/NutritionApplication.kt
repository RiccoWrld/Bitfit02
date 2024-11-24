package com.example.bitfit02

import android.app.Application

class NutritionApplication: Application() {
    val db by lazy { AppDatabase.getInstance(this)}
}