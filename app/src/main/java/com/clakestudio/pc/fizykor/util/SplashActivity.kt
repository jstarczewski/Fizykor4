package com.clakestudio.pc.fizykor.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clakestudio.pc.fizykor.equations.EquationsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, EquationsActivity::class.java))
        finish()
    }
}
