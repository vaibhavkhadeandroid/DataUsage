package com.vk.pd.datausage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.splashscreen.SplashScreen
import com.vk.pd.datausage.databinding.ActivitySplashScreenBinding
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        Handler().postDelayed({
            //doSomethingHere()
            var mySharedPreferences = MySharedPreferences.getExcludeAppsPrefs(baseContext)
            var setupfinished: Boolean =
                mySharedPreferences?.getBoolean(Constants.SETUP_FINISHED, false) as Boolean

            if (setupfinished) {
                startActivity(Intent(baseContext, MainActivity::class.java))


            } else {
                startActivity(Intent(baseContext, SetupActivity::class.java))


            }

        }, 1000)
    }
}


