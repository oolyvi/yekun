package com.oolyvi.yekun

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        // access token to reach from everywhere
        var ACCESS_TOKEN: String? = null
    }

}