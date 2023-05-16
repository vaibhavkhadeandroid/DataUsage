package com.vk.pd.datausage

object Constants {

    const val DATA_USAGE_NOTIFICATION_ID = 0x0045
    const val DATA_USAGE_NOTIFICATION_CHANNEL_ID = "DataUsage.Notification"
    const val DATA_USAGE_NOTIFICATION_CHANNEL_NAME = "Data Usage"
    const val DATA_USAGE_NOTIFICATION_NOTIFICATION_GROUP = "Data Usage"
    const val DATA_USAGE_WARNING_NOTIFICATION_ID = 0x00A0
    const val DATA_USAGE_WARNING_CHANNEL_ID = "DataUsage.Warning"
    const val DATA_USAGE_WARNING_CHANNEL_NAME = "Data Usage Warning"
    const val APP_DATA_USAGE_WARNING_NOTIFICATION_ID = 0x00BE // 190

    const val APP_DATA_USAGE_WARNING_CHANNEL_ID = "AppDataUsage.Warning"
    const val APP_DATA_USAGE_WARNING_CHANNEL_NAME = "App Data Usage Warning"
    const val NETWORK_SIGNAL_NOTIFICATION_ID = 0x010D
    const val NETWORK_SIGNAL_CHANNEL_ID = "NetworkSignal.Notification"
    const val NETWORK_SIGNAL_CHANNEL_NAME = "Network Speed Monitor"
    const val NETWORK_SIGNAL_NOTIFICATION_GROUP = "Network Speed Monitor"
    const val OTHER_NOTIFICATION_ID = 0x012C
    const val OTHER_NOTIFICATION_CHANNEL_ID = "Other.Notification"
    const val OTHER_NOTIFICATION_CHANNEL_NAME = "Other"
    const val DEFAULT_NOTIFICATION_GROUP = "Default"

    const val SETUP_FINISHED ="Setup_Finished"


    const val SESSION_TODAY = 0x000A
    const val SESSION_YESTERDAY = 0x0014
    const val SESSION_THIS_MONTH = 0x001E
    const val SESSION_LAST_MONTH = 0x0028
    const val SESSION_THIS_YEAR = 0x0032
    const val SESSION_ALL_TIME = 0x003C
    const val SESSION_MONTHLY = 0x00A9
    const val SESSION_CUSTOM = 0x00AC

    const val TYPE_MOBILE_DATA = 0x0046
    const val TYPE_WIFI = 0x0050

    const val DATA_USAGE_VALUE = "data_usage_value"
    const val DATA_USAGE_SESSION = "data_usage_session"
    const val DATA_USAGE_TYPE = "data_usage_type"
    const val DATA_USAGE_SYSTEM = 0x005A
    const val DATA_USAGE_USER = 0x0064
    const val DATA_USAGE_TODAY = 0x00D3

    const val GENERAL_FRAGMENT_ID = "GENERAL_FRAGMENT_ID"

    const val ABOUT_FRAGMENT = 0x006E
    const val LICENSE_FRAGMENT = 0x0078
    const val CONTRIBUTORS_FRAGMENT = 0x0082
    const val DONATE_FRAGMENT = 0x008C
    const val APP_LICENSE_FRAGMENT = 0x0096
    const val OSS_LICENSE_FRAGMENT = 0x00DC
    const val APP_DATA_LIMIT_FRAGMENT = 0x00AA
    const val NETWORK_STATS_FRAGMENT = 0x00C8
    const val APP_LANGUAGE_FRAGMENT = 0x00D2
    const val DISABLE_BATTERY_OPTIMISATION_FRAGMENT = 0x00E6
    const val DIAGNOSTICS_SETTINGS_FRAGMENT = 0x00F0
    const val EXCLUDE_APPS_FRAGMENT = 0x0104
    const val DIAGNOSTICS_HISTORY_FRAGMENT = 0x010E

    const val BOTTOM_NAVBAR_ITEM_HOME = 0
    const val BOTTOM_NAVBAR_ITEM_SETUP = 1
    const val BOTTOM_NAVBAR_ITEM_APP_DATA_USAGE = 2
    const val BOTTOM_NAVBAR_ITEM_SETTINGS = 3

    const val SETUP_VALUE = "SETUP_VALUE"
    const val USAGE_ACCESS_DISABLED = 0x00B4
    const val READ_PHONE_STATE_DISABLED = 0x00B5
    const val REQUEST_READ_PHONE_STATE = 2011
    const val REQUEST_POST_NOTIFICATIONS = 2111

    const val SETUP_COMPLETED = "is_setup_complete"
    const val DATA_LIMIT = "data_limit"
    const val DATA_TYPE = "data_type"
    const val LIMIT = "limit"
    const val DATA_RESET = "data_reset"
    const val DATA_RESET_DAILY = "daily"
    const val DATA_RESET_MONTHLY = "monthly"
    const val DATA_RESET_CUSTOM = "custom"
    const val DATA_RESET_HOUR = "reset_hour"
    const val DATA_RESET_MIN = "reset_min"
    const val DATA_RESET_DATE = "reset_date"
    const val DATA_RESET_CUSTOM_DATE_START = "custom_reset_date_start"
    const val DATA_RESET_CUSTOM_DATE_END = "custom_reset_date_end"
    const val DATA_RESET_CUSTOM_DATE_RESTART = "custom_reset_date_restart"
    const val DATA_WARNING_TRIGGER_LEVEL = "data_warning_trigger_level"
    const val DATA_USAGE_WARNING_SHOWN = "data_usage_warning_shown"
    const val DATA_USAGE_ALERT = "data_usage_alert"
    const val WIDGET_REFRESH_INTERVAL_SUMMARY = "widget_refresh_interval_summary"
    const val WIDGET_REFRESH_INTERVAL = "widget_refresh_interval"
    const val NOTIFICATION_REFRESH_INTERVAL_SUMMARY = "notification_refresh_interval_summary"
    const val NOTIFICATION_REFRESH_INTERVAL = "notification_refresh_interval"
    const val NOTIFICATION_MOBILE_DATA = "notification_mobile_data"
    const val NOTIFICATION_WIFI = "notification_wifi"
    const val APP_LANGUAGE = "app_language"
    const val APP_LANGUAGE_CODE = "app_language_code"
    const val APP_COUNTRY_CODE = "app_country_code"
    const val DAILY_DATA_HOME_ACTION = "daily_data_home_action"
    const val APP_THEME = "app_theme"
    const val APP_THEME_SUMMARY = "app_theme_summary"
    const val DIAGNOSTICS_DOWNLOAD_URL = "diagnostics_download_url"
    const val DIAGNOSTICS_DOWNLOAD_URL_SUMMARY = "diagnostics_download_url_summary"
    const val DIAGNOSTICS_DOWNLOAD_URL_INDEX = "diagnostics_download_url_index"
    const val DIAGNOSTICS_UPLOAD_URL = "diagnostics_upload_url"
    const val DIAGNOSTICS_UPLOAD_URL_SUMMARY = "diagnostics_upload_url_summary"
    const val DIAGNOSTICS_UPLOAD_URL_INDEX = "diagnostics_upload_url_index"
    const val SHOW_ADD_PLAN_BANNER = "show_add_plan_banner"
    const val LANGUAGE_SYSTEM_DEFAULT = "system"

    const val DARK_MODE_TOGGLE = "dark_mode_toggle"

    const val USAGE_SESSION_TODAY = "Today"
    const val USAGE_SESSION_YESTERDAY = "Yesterday"
    const val USAGE_SESSION_THIS_MONTH = "This Month"
    const val USAGE_SESSION_LAST_MONTH = "Last Month"
    const val USAGE_SESSION_THIS_YEAR = "This Year"
    const val USAGE_SESSION_ALL_TIME = "All Time"

    const val USAGE_TYPE_MOBILE_DATA = "Mobile Data"
    const val USAGE_TYPE_WIFI = "Wifi"

    const val MAX_DOWNLOAD_SPEED = "max_download_speed"
    const val AVG_DOWNLOAD_SPEED = "avg_download_speed"
    const val MAX_UPLOAD_SPEED = "max_upload_speed"
    const val AVG_UPLOAD_SPEED = "avg_upload_speed"
    const val MIN_LATENCY = "min_latency"
    const val AVG_LATENCY = "avg_latency"
    const val NETWORK_IP = "network_ip"
    const val ISP = "isp"
    const val SERVER = "server"
    const val REGION = "region"

    const val UPDATE_VERSION = "update_version"
    const val MD5_GITHUB = "39aa537128b70c2886cb771c33944a7d"
    const val MD5_PLAY = "58CDB2B01A9E512D4FDC1A9926A29513"
    const val MD5_F_DROID = "4a509c658ae252568c7c196c0dba01ae"

    const val CRASH_REPORT_KEY = "datamonitor.crashReport"

    const val INTENT_ACTION = "datamonitor.intent.action"
    const val ACTION_SHOW_DATA_PLAN_NOTIFICATION = "datamonitor.intent.action.dataPlanNotification"
    const val EXTRA_APP_NAME = "datamonitor.intent.extra.appName"
    const val EXTRA_APP_PACKAGE = "datamonitor.intent.extra.appPackageName"

    const val ICON_DATA_USAGE = "combined_icon_data_usage"
    const val ICON_NETWORK_SPEED = "combined_icon_network_speed"

    const val EXCLUDE_APPS_PREFERENCES = "com.drnoob.datamonitor_exclude_apps_preferences"
    const val DIAGNOSTICS_HISTORY_PREFERENCES =
        "com.drnoob.datamonitor_diagnostics_history_preferences"
    const val EXCLUDE_APPS_LIST = "excluded_apps_list"
    const val DIAGNOSTICS_HISTORY_LIST = "diagnostics_history_list"

}