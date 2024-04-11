package com.healthcare.models

data class LabTestPackage(
    val name: String = "",
    val tests: List<String> = emptyList(),
    val price: String = "",
    val instructions: String = ""
)