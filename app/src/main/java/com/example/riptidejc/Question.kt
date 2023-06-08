package com.example.riptidejc

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Question(val header: String? = null, val body: String? = null,
                    val courseId: String? = null, val user: String? = null,
                    val complete: Boolean = false)
