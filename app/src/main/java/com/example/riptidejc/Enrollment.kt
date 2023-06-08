package com.example.riptidejc

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Enrollment(val courseId: String? = null, val studentId: String? = null)
