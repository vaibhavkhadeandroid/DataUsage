package com.vk.pd.datausage.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.vk.pd.datausage.MainActivity
import com.vk.pd.datausage.R
import com.vk.pd.datausage.databinding.FragmentRequestPhoneStatePermissionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RequestPhoneStatePermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestPhoneStatePermissionFragment : Fragment() {
    private val REQUEST_READ_PHONE_STATE: Int=12345
    private lateinit var  binding: FragmentRequestPhoneStatePermissionBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding =  FragmentRequestPhoneStatePermissionBinding.inflate(inflater)
        binding.btnprimary .setOnClickListener(View.OnClickListener
        {
            activity?.supportFragmentManager?.commit {
              var  intent = Intent(context, MainActivity::class.java)
                startActivity(intent)

//                val myFragment = DisableBatteryOptimisationFragment()
//                // add(R.id.fragment_container, myFragment)
//                replace(R.id.fragment_container, myFragment)
//                setReorderingAllowed(true)
//                addToBackStack(null)
            }
        })

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }


        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_PHONE_STATE
                )
            } == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                Log.e("permissionResponce","granted1");

            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
//                requestPermissionLauncher.launch(
//                    Manifest.permission.READ_PHONE_STATE)
                requestPermissions(
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUEST_READ_PHONE_STATE)
            }
        }



        return binding.root;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestPhoneStatePermissionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RequestPhoneStatePermissionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_READ_PHONE_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Log.e("permissionResponce","granted");
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    Log.e("permissionResponce","denied");

                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}