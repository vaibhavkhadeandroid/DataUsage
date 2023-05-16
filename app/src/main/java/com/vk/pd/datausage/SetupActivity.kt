package com.vk.pd.datausage

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.vk.pd.datausage.databinding.ActivitySetupBinding
import com.vk.pd.datausage.fragments.DisableBatteryOptimisationFragment
import com.vk.pd.datausage.fragments.RequestPhoneStatePermissionFragment
import com.vk.pd.datausage.fragments.RequestUsagePermissionFragment
import com.vk.pd.datausage.fragments.SetupFragment


class SetupActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivitySetupBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
      var mySharedPreferences=  MySharedPreferences.getExcludeAppsPrefs(baseContext)
     var mySharedPreferencesedit= mySharedPreferences?.edit()
        mySharedPreferencesedit?.putBoolean(Constants.SETUP_FINISHED,true);
        mySharedPreferencesedit?.commit()
//        binding. fragmentprogress.progressDrawable= R.drawable.progressbarbacgrounddrawble
//        binding. fragmentprogress.getProgressDrawable()?.setColorFilter(
//            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//        Use progressIndicator.setProgressCompat((int) value, true); to update the value in the indicator.
//        Use the attributes:
//        indicatorColor to se the color of the color of indicator
//        trackColor to set the color of track
//        trackThickness to set the height of the progress bar

//        binding. fragmentprogress.progress=30
        binding.fragmentprogress.setProgressCompat(60,true)
        supportFragmentManager.commit {
            replace(R.id.fragment_container,SetupFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                if (f is SetupFragment ) {
                    binding.fragmentprogress.setProgressCompat(25,true)
                } else if (f is DisableBatteryOptimisationFragment) {
                    binding.fragmentprogress.setProgressCompat(60,true)

                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        if (f is RequestUsagePermissionFragment) {
                            binding.fragmentprogress.setProgressCompat(80,true)

                        } else if (f is RequestPhoneStatePermissionFragment) {
                            binding.fragmentprogress.setProgressCompat(100,true)

                        }
                    } else {
                        if (f is RequestUsagePermissionFragment) {
                            binding.fragmentprogress.setProgressCompat(100,true)

                        }
                    }
                }
            }
        }, true)


    }
}