package com.example.housemaster

data class UserProfileModel(
    val userId: String,
    val fName: String,
    val lName: String,
    val email: String,
    val mobile: String,
    val streetAddress: String,
    val province: String,
    val suiteAptNo: String,
    val city: String,
    val postalCode: String
)
