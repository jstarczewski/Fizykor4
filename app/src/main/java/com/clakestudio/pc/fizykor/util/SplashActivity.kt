package com.clakestudio.pc.fizykor.util

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.clakestudio.pc.fizykor.equations.EquationsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, EquationsActivity::class.java))
        finish()
    }
}
