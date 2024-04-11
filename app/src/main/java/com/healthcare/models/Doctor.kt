package com.healthcare.models

data class Doctor(
    val name: String,
    val specialty: String,
    val qualifications: String,
    val availability: String,
    val reviews: List<String>
)

