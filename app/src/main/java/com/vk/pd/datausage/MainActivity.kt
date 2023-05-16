package com.vk.pd.datausage

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vk.pd.datausage.Constants.SESSION_TODAY
import com.vk.pd.datausage.Constants.TYPE_MOBILE_DATA
import com.vk.pd.datausage.Utils.Companion.formatData
import com.vk.pd.datausage.Utils.Companion.getDeviceMobileDataUsage
import com.vk.pd.datausage.databinding.ActivityMainBinding
import com.vk.pd.datausage.models.AppDataUsageModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var   modelList: MutableList<AppDataUsageModel>

    private lateinit var binding: ActivityMainBinding
    companion object{
        var mSystemAppsList: MutableList<AppDataUsageModel> = java.util.ArrayList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        Log.e("dataload","oncreated");
binding.checkinterbnt.setOnClickListener {

   var status = Utils.checkForInternet(applicationContext)
    Log.e("jvhjfbh",""+status)
}


    }


    override fun onStart() {
        super.onStart()


    val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
    val mode:Int
    if (Build.VERSION.SDK_INT >= VERSION_CODES.Q) {
        mode= appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
    } else {
        mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
    }
    if (mode == AppOpsManager.MODE_ALLOWED) {
        GlobalScope.launch(Dispatchers.Main) {

            var totaldata= async { LoadTotaldata(baseContext, SESSION_TODAY, TYPE_MOBILE_DATA) }

            var totalmobiledatat=totaldata.await()
        }
        GlobalScope.launch(Dispatchers.Main) {
            var applist = async { fetchApps()
            }
            var result = applist.await()
        }

    }
    else{
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }



       binding. recyclerView.layoutManager = LinearLayoutManager(this)

        val languages = resources.getStringArray(R.array.DAYES)

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, languages)
        binding. daysselection.adapter = adapter

//        binding. daysselection.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>,
//                                        view: View, position: Int, id: Long) {
//                Toast.makeText(this@MainActivity,
//                    getString(R.string.selected_item) + " " +
//                            "" + languages[position], Toast.LENGTH_SHORT).show()
//
//                when(languages[position]){
//                    languages[0] ->
//                        LoadData(baseContext, SESSION_TODAY, TYPE_MOBILE_DATA)
//
//                    languages[1] ->
//                        LoadData(baseContext, SESSION_YESTERDAY, TYPE_MOBILE_DATA)
//
//                    languages[2] ->
//                        LoadData(baseContext, SESSION_MONTHLY, TYPE_MOBILE_DATA)
//
//                    languages[3] ->
//                        LoadData(baseContext, SESSION_LAST_MONTH, TYPE_MOBILE_DATA)
//
//                    languages[4] ->
//                        LoadData(baseContext, SESSION_THIS_YEAR, TYPE_MOBILE_DATA)
//
//                    languages[5] ->
//                        LoadData(baseContext, SESSION_ALL_TIME, TYPE_MOBILE_DATA)
//
//                }
//
//               // LoadData(baseContext, SESSION_MONTHLY, TYPE_MOBILE_DATA)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // write code to perform some action
//            }
//        }

    }

    private suspend fun fetchApps(): MutableList<AppDataUsageModel> {
        Log.e("dataload","fechstart");
        //Inside coroutine scopes (like inside async here), delay is used instead of Thread.sleep.
            val packageManager = baseContext.packageManager
            val allApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

//            val apps = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA and PackageManager.GET_SHARED_LIBRARY_FILES)
            Log.e("count", "" + allApps.size)
//            Log.e("count", "" + apps.size)
            modelList = ArrayList<AppDataUsageModel>()
            var model: AppDataUsageModel? = null
//            val databaseHandler = DatabaseHandler(mContext)
            for (applicationInfo in allApps) {
                if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
                    // System app
                    modelList.add(
                        AppDataUsageModel(
                            packageManager.getApplicationLabel(applicationInfo).toString(),
                            applicationInfo.packageName,
                            applicationInfo.uid,
                            true
                        )
                    )
                } else {
                    // User app
                    modelList.add(
                        AppDataUsageModel(
                            packageManager.getApplicationLabel(applicationInfo).toString(),
                            applicationInfo.packageName,
                            applicationInfo.uid,
                            false
                        )
                    )
                }
            }
//            for (i in modelList.indices) {
//                var name = modelList.get(i).appName
//                Log.e("namess",name.toString())
//            }
           //  LoadData(baseContext, SESSION_MONTHLY, TYPE_MOBILE_DATA)
        Log.e("dataload","fechend");
        mSystemAppsList.addAll(modelList)
        return modelList;
        }



    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


    private suspend fun LoadTotaldata(
        context: Context,
        session: Int,
        type: Int): Long {
        Log.e("datausage1","start")
       // getDeviceMobileDataUsage(getContext(), SESSION_TODAY, 1);
        var datta=     getDeviceMobileDataUsage(context, SESSION_TODAY, 1)
        val mobileData = datta?.let { formatData(it.get(0), datta.get(1)) }

        Log.e("hgjjsg", mobileData?.get(0) +mobileData?.get(1)+mobileData?.get(2)?: "")
binding.totalconsumeddata.text= mobileData?.get(1) ?:""
        return 1385

    }


}
