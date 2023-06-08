package com.example.riptidejc

import android.media.Image
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val name: String? = null, val email: String? = null, val role: String? = null,
                val profImg: Image? = null)
