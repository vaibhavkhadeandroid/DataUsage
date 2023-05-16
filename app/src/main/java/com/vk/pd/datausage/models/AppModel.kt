package com.vk.pd.datausage.models

import java.io.Serializable



class AppModel : Serializable {
    var appName: String? = null
    var packageName: String? = null
    var isSystemApp: Boolean? = null
    var isSelected = false

    constructor() {
        // Empty constructor
    }

    constructor(appName: String?, packageName: String?) {
        this.appName = appName
        this.packageName = packageName
    }

    constructor(appName: String?, packageName: String?, isSystemApp: Boolean?) {
        this.appName = appName
        this.packageName = packageName
        this.isSystemApp = isSystemApp
    }
}
