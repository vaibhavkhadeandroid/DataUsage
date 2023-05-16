package com.vk.pd.datausage.models

import android.graphics.drawable.Drawable
import java.io.Serializable


class AppDataUsageModel : Serializable {
    var appName: String? = null
    var packageName: String? = null
    var totalDataUsage: String? = null
    var appIcon: Drawable? = null
    var sentMobile: Long = 0
    var sentWifi: Long = 0
    var receivedMobile: Long = 0
    var receivedWifi: Long = 0
    var mobileTotal: Float? = null
    var wifiTotal: Float? = null
    var uid = 0
    var session = 0
    var type = 0
    var progress = 0
    var isSystemApp: Boolean? = null
    var isAppsList: Boolean? = null
    var dataLimit: String? = null
    var dataType: String? = null
    var list: List<AppDataUsageModel>? = null

    constructor() {}
    constructor(mAppName: String?, mAppIcon: Drawable?, mTotalDataUsage: String?) {
        appName = mAppName
        appIcon = mAppIcon
        totalDataUsage = mTotalDataUsage
    }

    constructor(
        mAppName: String?,
        mTotalDataUsage: String?,
        mAppIcon: Drawable?,
        mMobileTotal: Float?
    ) {
        appName = mAppName
        totalDataUsage = mTotalDataUsage
        appIcon = mAppIcon
        mobileTotal = mMobileTotal
    }

    constructor(mAppName: String?, mPackageName: String?, uid: Int, isSystemApp: Boolean?) {
        appName = mAppName
        packageName = mPackageName
        this.uid = uid
        this.isSystemApp = isSystemApp
    }

    constructor(
        mAppName: String?,
        mPackageName: String?,
        uid: Int,
        isSystemApp: Boolean?,
        isAppsList: Boolean?
    ) {
        appName = mAppName
        packageName = mPackageName
        this.uid = uid
        this.isSystemApp = isSystemApp
        this.isAppsList = isAppsList
    }
}
