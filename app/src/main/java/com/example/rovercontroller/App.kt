package com.example.rovercontroller

import android.app.Application
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class App: Application()
{
    override fun onCreate()
    {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}