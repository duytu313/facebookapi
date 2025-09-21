package com.example.facebook

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val id: String,
    val name: String,
    val avatarRes: Int?,   // resource id
    val status: String
) : Parcelable
