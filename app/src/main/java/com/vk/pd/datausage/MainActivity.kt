package com.vk.pd.datausage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vk.pd.datausage.databinding.ActivityMainBinding
import com.vk.pd.datausage.databinding.ActivitySetupBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
binding.checkinterbnt.setOnClickListener {

   var status = Utils.checkForInternet(applicationContext)
    Log.e("jvhjfbh",""+status)
}
    }
}
