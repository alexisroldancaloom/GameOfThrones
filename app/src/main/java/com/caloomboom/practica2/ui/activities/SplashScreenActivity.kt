package com.caloomboom.practica2.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.caloomboom.practica2.Logging
import com.caloomboom.practica2.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, Logging::class.java)
            startActivity(intent)
            finish() // Finaliza el SplashScreen para evitar que el usuario pueda volver atrás
        }, 2000)
    }
}
