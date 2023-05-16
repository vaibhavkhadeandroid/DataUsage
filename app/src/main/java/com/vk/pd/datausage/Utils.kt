package com.vk.pd.datausage

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.RemoteException
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.pd.datausage.Constants.DATA_RESET_CUSTOM_DATE_END
import com.vk.pd.datausage.Constants.DATA_RESET_CUSTOM_DATE_START
import com.vk.pd.datausage.Constants.DATA_RESET_DATE
import com.vk.pd.datausage.Constants.SESSION_ALL_TIME
import com.vk.pd.datausage.Constants.SESSION_CUSTOM
import com.vk.pd.datausage.Constants.SESSION_LAST_MONTH
import com.vk.pd.datausage.Constants.SESSION_MONTHLY
import com.vk.pd.datausage.Constants.SESSION_THIS_MONTH
import com.vk.pd.datausage.Constants.SESSION_THIS_YEAR
import com.vk.pd.datausage.Constants.SESSION_TODAY
import com.vk.pd.datausage.Constants.SESSION_YESTERDAY
import com.vk.pd.datausage.models.AppModel
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {


        private val gson: Gson = Gson()
        private val type: Type = object : TypeToken<List<AppModel?>?>() {}.getType()
        public fun checkForInternet(context: Context): Boolean {

            // register activity with the connectivity manager service
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // if the android version is equal to M
            // or greater we need to use the
            // NetworkCapabilities to check what type of
            // network has the internet connection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Returns a Network object corresponding to
                // the currently active default data network.
                val network = connectivityManager.activeNetwork ?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Indicates this network uses a Cellular transport. or
                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // else return false
                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }

        }
         fun   getAppMobileDataUsage(context: Context,uid:Int ,session :Int): Array<Long> {



         var networkStatsManager: NetworkStatsManager =context.applicationContext.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

           var  networkStats : NetworkStats? =null

             var total = 0L
             var sent = 0L
             var received = 0L

             val resetTimeMillis: Long = getTimePeriod(context, session, 1).get(0)

             val endTimeMillis: Long = getTimePeriod(context, session, 1).get(1)
             networkStats = networkStatsManager.querySummary(
                 ConnectivityManager.TYPE_MOBILE,
                 getSubscriberId(context),
                 resetTimeMillis,
                 endTimeMillis
             )
//             val tm = context.applicationContext.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//             val subscriberID = tm.subscriberId
//
//             val networkStatsByApp = networkStatsManager.queryDetailsForUid(
//                 ConnectivityManager.TYPE_MOBILE,
//                 subscriberID,
//                 resetTimeMillis,
//                 endTimeMillis,
//                 uid
//             )


             do {
                 val bucket = NetworkStats.Bucket()
                 networkStats.getNextBucket(bucket)


                 if (bucket.uid.toInt() == uid.toInt()) {
                     sent = sent + bucket.txBytes
                     received = received + bucket.rxBytes

                 }
             } while (networkStats.hasNextBucket())

             total = sent + received
             networkStats.close()

             val data = arrayOf(sent, received, total)

             return data
         }


        @Throws(ParseException::class)
        fun getTimePeriod(context: Context, session: Int, startDate: Int): Array<Long> {
            var year: Int
            var month: Int
            var day: Int
            var resetTimeMillis = 0L
            var endTimeMillis = 0L
            val resetHour: Int = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt("reset_hour", 0)
            val resetMin: Int = PreferenceManager.getDefaultSharedPreferences(context)
                .getInt("reset_min", 0)
            val date = Date()
            val yearFormat = SimpleDateFormat("yyyy")
            val monthFormat = SimpleDateFormat("MM")
            val dayFormat = SimpleDateFormat("dd")
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            var startTime: String
            var endTime: String
            var resetDate: Date
            var endDate: Date
            val calendar = Calendar.getInstance()
            val monthlyResetDate: Int =
                PreferenceManager.getDefaultSharedPreferences(context).getInt(DATA_RESET_DATE, 1)
            val today = calendar[Calendar.DAY_OF_MONTH] + 1
            when (session) {
                SESSION_TODAY -> {
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = dayFormat.format(date).toInt()
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                    calendar.add(Calendar.DATE, 1)
                }
                SESSION_YESTERDAY -> {
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = dayFormat.format(date).toInt() - 1
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    day = dayFormat.format(date).toInt()
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
                SESSION_THIS_MONTH -> {
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = startDate
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
                SESSION_LAST_MONTH -> //                year = Integer.parseInt(yearFormat.format(date));
//                month = Integer.parseInt(monthFormat.format(date)) - 1;
//                day = 1;
//                startTime = context.getResources().getString(com.vk.pd.datausage.R.string.reset_time, year, month, day, resetHour, resetMin);
//                resetDate = dateFormat.parse(startTime);
//                resetTimeMillis = resetDate.getTime();
//
//                month = Integer.parseInt(monthFormat.format(date));
//                endTime = context.getResources().getString(com.vk.pd.datausage.R.string.reset_time, year, month, day, resetHour, resetMin);
//                endDate = dateFormat.parse(endTime);
//                endTimeMillis = endDate.getTime();
                    /**
                     * When data reset date is ahead of today's date, reducing 1 from the current month will
                     * only give the month when the current plan started.
                     * So to get the last month's period, 2 has to be subtracted to get the starting month
                     * and 1 to get the ending month
                     * For eg: Today is 4th of August and plan resets on 8th of August, subtracting 2 & 1
                     * respectively will wive the period of June 8th to July 8th, i.e period of last month.
                     */
                    if (monthlyResetDate >= today) {
                        // Time period from reset date of previous month till today
                        year = yearFormat.format(date).toInt()
                        month = monthFormat.format(date).toInt() - 2
                        day = monthlyResetDate
                        startTime = context.resources.getString(
                            com.vk.pd.datausage.R.string.reset_time,
                            year,
                            month,
                            day,
                            resetHour,
                            resetMin
                        )
                        resetDate = dateFormat.parse(startTime)
                        resetTimeMillis = resetDate.time
                        month = monthFormat.format(date).toInt() - 1
                        day = monthlyResetDate
                        endTime = context.resources.getString(
                            com.vk.pd.datausage.R.string.reset_time,
                            year,
                            month,
                            day,
                            resetHour,
                            resetMin
                        )
                        endDate = dateFormat.parse(endTime)
                        endTimeMillis = endDate.time
                    } else {
                        // Reset date is in the current month.
                        year = yearFormat.format(date).toInt()
                        month = monthFormat.format(date).toInt() - 1
                        day = monthlyResetDate
                        startTime = context.resources.getString(
                            com.vk.pd.datausage.R.string.reset_time,
                            year,
                            month,
                            day,
                            resetHour,
                            resetMin
                        )
                        resetDate = dateFormat.parse(startTime)
                        resetTimeMillis = resetDate.time
                        day = monthlyResetDate
                        endTime = context.resources.getString(
                            com.vk.pd.datausage.R.string.reset_time,
                            year,
                            month,
                            day,
                            resetHour,
                            resetMin
                        )
                        endDate = dateFormat.parse(endTime)
                        endTimeMillis = endDate.time
                    }
                SESSION_THIS_YEAR -> {
                    year = yearFormat.format(date).toInt()
                    month = 1
                    day = 1
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    month = monthFormat.format(date).toInt()
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
                SESSION_ALL_TIME -> {
                    resetTimeMillis = 0L
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
                SESSION_MONTHLY -> if (monthlyResetDate >= today) {
                    // Time period from reset date of previous month till today
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt() - 1
                    day = monthlyResetDate
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    month = monthFormat.format(date).toInt()
                    day = today
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                } else {
                    // Reset date is in the current month.
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = monthlyResetDate
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
                SESSION_CUSTOM -> {
                    //                year = Integer.parseInt(yearFormat.format(date));
//                month = Integer.parseInt(monthFormat.format(date));
//                day = PreferenceManager.getDefaultSharedPreferences(context).getInt(DATA_RESET_CUSTOM_DATE_START, 1);
//                startTime = context.getResources().getString(com.vk.pd.datausage.R.string.reset_time, year, month, day, resetHour, resetMin);
//                resetDate = dateFormat.parse(startTime);
                    resetTimeMillis = PreferenceManager.getDefaultSharedPreferences(context)
                        .getLong(DATA_RESET_CUSTOM_DATE_START, Date().time)
                    //                day = PreferenceManager.getDefaultSharedPreferences(context).getInt(DATA_RESET_CUSTOM_DATE_END
//                        , calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//                endTime = context.getResources().getString(com.vk.pd.datausage.R.string.reset_time, year, month, day, 23, 59);
//                endDate = dateFormat.parse(endTime);
                    endTimeMillis = PreferenceManager.getDefaultSharedPreferences(context)
                        .getLong(DATA_RESET_CUSTOM_DATE_END, Date().time)
                }
            }
            if (resetTimeMillis > System.currentTimeMillis()) {
                year = yearFormat.format(date).toInt()
                month = monthFormat.format(date).toInt()
                day = dayFormat.format(date).toInt()
                day = day - 1
                startTime = context.resources.getString(
                    com.vk.pd.datausage.R.string.reset_time,
                    year,
                    month,
                    day,
                    resetHour,
                    resetMin
                )
                resetDate = dateFormat.parse(startTime)
                resetTimeMillis = resetDate.time
                startTime = context.resources.getString(
                    com.vk.pd.datausage.R.string.reset_time,
                    year,
                    month,
                    day,
                    resetHour,
                    resetMin
                )
                resetDate = dateFormat.parse(startTime)
                resetTimeMillis = resetDate.time
                day = dayFormat.format(date).toInt()
                endTime = context.resources.getString(
                    com.vk.pd.datausage.R.string.reset_time,
                    year,
                    month,
                    day,
                    resetHour,
                    resetMin
                )
                endDate = dateFormat.parse(endTime)
                endTimeMillis = endDate.time
            } else {
                if (session == SESSION_TODAY) {
                    year = yearFormat.format(date).toInt()
                    month = monthFormat.format(date).toInt()
                    day = dayFormat.format(date).toInt()
                    startTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    resetDate = dateFormat.parse(startTime)
                    resetTimeMillis = resetDate.time
                    day = dayFormat.format(date).toInt() + 1
                    endTime = context.resources.getString(
                        com.vk.pd.datausage.R.string.reset_time,
                        year,
                        month,
                        day,
                        resetHour,
                        resetMin
                    )
                    endDate = dateFormat.parse(endTime)
                    endTimeMillis = endDate.time
                }
            }
            return arrayOf(resetTimeMillis, endTimeMillis)
        }

        fun getSubscriberId(context: Context): String? {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                var subscriberId: String? = ""
                try {
                    subscriberId = telephonyManager.subscriberId
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                subscriberId
            } else {
                null
            }
        }

        @Throws(ParseException::class, RemoteException::class)
        fun getDeviceMobileDataUsage(
            context: Context,
            session: Int,
            startDate: Int
        ): Array<Long>? {
            val networkStatsManager =
                context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
            val networkStats: NetworkStats? = null
            var bucket = NetworkStats.Bucket()
            val resetTimeMillis: Long =
                getTimePeriod(
                    context,
                    session,
                    startDate
                ).get(0)
            val endTimeMillis: Long =
                getTimePeriod(
                    context,
                    session,
                    startDate
                ).get(1)
            var sent = 0L
            var received = 0L
            var total = 0L
            var excludedSent = 0L
            var excludedReceived = 0L
            var excludedTotal = 0L
//            val jsonData: String = SharedPreferences.getExcludeAppsPrefs(context)
//                .getString(EXCLUDE_APPS_LIST, null)
//            val excludedAppsList: MutableList<AppModel> =
//                ArrayList()
//            if (jsonData != null) {
//                excludedAppsList.addAll(
//                    com.drnoob.datamonitor.utils.NetworkStatsHelper.gson.fromJson<Collection<AppModel?>>(
//                        jsonData,
//                        com.drnoob.datamonitor.utils.NetworkStatsHelper.type
//                    )
//                )
//            }
//            for (app in MainActivity.mSystemAppsList) {
//                var uid = 0
//                try {
//                    uid = app.packageName?.let { context.packageManager.getApplicationInfo(it, 0).uid }!!
//                } catch (e: PackageManager.NameNotFoundException) {
//                    e.printStackTrace()
//                }
//                val mobile: Array<Long> =
//                    getAppMobileDataUsage(
//                        context,
//                        uid,
//                        session
//                    )
//                excludedSent += mobile[0]
//                excludedReceived += mobile[1]
//                excludedTotal += mobile[2]
//            }
            bucket = networkStatsManager.querySummaryForDevice(
                ConnectivityManager.TYPE_MOBILE,
                getSubscriberId(context),
                resetTimeMillis,
                endTimeMillis
            )
            val rxBytes = bucket.rxBytes
            val txBytes = bucket.txBytes
            sent = txBytes
            received = rxBytes
            total = sent + received
            sent = sent - excludedSent
            received = received - excludedReceived
            total = total - excludedTotal
            return arrayOf(sent, received, total)
        }
        fun formatData(sent: Long, received: Long): Array<String> {
            val total = sent + received
            val data: Array<String>
            val totalBytes = total / 1024f
            val sentBytes = sent / 1024f
            val receivedBytes = received / 1024f
            val totalMB = totalBytes / 1024f
            val totalGB: Float
            val sentGB: Float
            val sentMB: Float
            val receivedGB: Float
            val receivedMB: Float
            sentMB = sentBytes / 1024f
            receivedMB = receivedBytes / 1024f
            var sentData = ""
            var receivedData = ""
            val totalData: String
            if (totalMB > 1024) {
                totalGB = totalMB / 1024f
                totalData = String.format("%.2f", totalGB) + " GB"
            } else {
                totalData = String.format("%.2f", totalMB) + " MB"
            }
            if (sentMB > 1024) {
                sentGB = sentMB / 1024f
                sentData = String.format("%.2f", sentGB) + " GB"
            } else {
                sentData = String.format("%.2f", sentMB) + " MB"
            }
            if (receivedMB > 1024) {
                receivedGB = receivedMB / 1024f
                receivedData = String.format("%.2f", receivedGB) + " GB"
            } else {
                receivedData = String.format("%.2f", receivedMB) + " MB"
            }
            data = arrayOf(sentData, receivedData, totalData)
            return data
        }

//        @Throws(ParseException::class, RemoteException::class)
//        fun getDeviceMobileDataUsage(
//            context: Context,
//            session: Int,
//            startDate: Int,
//            result: MutableList<AppDataUsageModel>
//        ): Array<Any>? {
//            val networkStatsManager =
//                context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
//            val networkStats: NetworkStats? = null
//            var bucket = NetworkStats.Bucket()
//            val resetTimeMillis: Long = getTimePeriod(context,session,startDate).get(0)
//            val endTimeMillis: Long = getTimePeriod(context, session, startDate).get(1)
//            var sent = 0L
//            var received = 0L
//            var total = 0L
//            var excludedSent = 0L
//            var excludedReceived = 0L
//            var excludedTotal = 0L
//            var drawble = context.resources.getDrawable(com.vk.pd.datausage.R.drawable.img,context.theme)
//
////            val jsonData: String? = MySharedPreferences.getExcludeAppsPrefs(context)
////                ?.getString(EXCLUDE_APPS_LIST, null)
////            val excludedAppsList: MutableList<AppModel> =
////                ArrayList<AppModel>()
////            if (jsonData != null) {
////                excludedAppsList.addAll(
////                    gson.fromJson(
////                        jsonData,
////                        type
////                    )
////                )
////            }
//
//
//            for (app in result) {
//
//
//
////                try {
////
////
////                    drawble=  app.packageName?.let { context.packageManager.getApplicationIcon(it) }!!
////
////                } catch (e: PackageManager.NameNotFoundException) {
////                    e.printStackTrace()
////                }
//                val mobile: Array<Long> =
//                   getAppMobileDataUsage(
//                        context,
//                        app.uid,
//                        session
//                    )
//                excludedSent += mobile[0]
//                excludedReceived += mobile[1]
//                excludedTotal += mobile[2]
//            }
//            bucket = networkStatsManager.querySummaryForDevice(
//                ConnectivityManager.TYPE_MOBILE,
//                getSubscriberId(context),
//                resetTimeMillis,
//                endTimeMillis
//            )
//            val rxBytes = bucket.rxBytes
//            val txBytes = bucket.txBytes
//            sent = txBytes
//            received = rxBytes
//            total = sent + received
//            sent = sent - excludedSent
//            received = received - excludedReceived
//            total = total - excludedTotal
//            return arrayOf(sent, received, total,drawble)
//        }


    }
}