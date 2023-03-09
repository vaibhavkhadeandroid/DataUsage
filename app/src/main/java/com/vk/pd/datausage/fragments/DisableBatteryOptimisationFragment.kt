package com.vk.pd.datausage.fragments

import android.Manifest
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.vk.pd.datausage.MainActivity
import com.vk.pd.datausage.R
import com.vk.pd.datausage.databinding.FragmentDisableBatteryOptimisationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisableBatteryOptimisationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisableBatteryOptimisationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDisableBatteryOptimisationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentDisableBatteryOptimisationBinding.inflate(inflater)
        binding.oemBatterySettings.setOnClickListener(View.OnClickListener {

            val OemSettingsIntent = Intent("android.settings.APP_BATTERY_SETTINGS")
            val OemSettingsUri = Uri.fromParts("package", context?.packageName, null)
            OemSettingsIntent.data = OemSettingsUri
            OemSettingsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(OemSettingsIntent)

        })
        binding.disableBatteryOptimisation.setOnClickListener(View.OnClickListener {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            val uri = Uri.fromParts("package", context?.packageName, null)
            intent.data = uri
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
        binding.next.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                try {
                    if (isUsageAccessGranted(context)) {
                        if (isReadPhoneStateGranted(context)) {
                            startActivity(Intent(context, MainActivity::class.java))
                            activity?.finish()
                        } else {

                            activity?.supportFragmentManager?.commit {

                                val myFragment = RequestPhoneStatePermissionFragment()
                                // add(R.id.fragment_container, myFragment)
                                replace(R.id.fragment_container, myFragment)
                                setReorderingAllowed(true)
                                addToBackStack(null)
                            }

                        }
                    } else {
                        activity?.supportFragmentManager?.commit {

                            val myFragment = RequestPhoneStatePermissionFragment()
                            // add(R.id.fragment_container, myFragment)
                            replace(R.id.fragment_container, myFragment)
                            setReorderingAllowed(true)
                            addToBackStack(null)
                        }
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    if (isUsageAccessGranted(context)) {
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()
                    } else {
                        activity?.supportFragmentManager?.commit {

                            val myFragment = RequestPhoneStatePermissionFragment()
                            // add(R.id.fragment_container, myFragment)
                            replace(R.id.fragment_container, myFragment)
                            setReorderingAllowed(true)
                            addToBackStack(null)
                        }
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
            }

        })



        return binding.root;

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisableBatteryOptimisationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisableBatteryOptimisationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        @Throws(PackageManager.NameNotFoundException::class)
        fun isUsageAccessGranted(context: Context?): Boolean {
            val applicationInfo = context?.packageManager?.getApplicationInfo(context.packageName, 0)
            val appOpsManager = context?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = applicationInfo?.let {
                appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    it.uid,
                    applicationInfo.packageName
                )
            }
            return mode == AppOpsManager.MODE_ALLOWED
        }

        fun isReadPhoneStateGranted(context: Context?): Boolean {
            return (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_PHONE_STATE
            )
                    == PackageManager.PERMISSION_GRANTED)
        }
    }
}