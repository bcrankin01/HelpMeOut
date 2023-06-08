package com.example.riptidejc

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Course(val name: String? = null, val size: Int? = 0, val teacherId: String? = null)
