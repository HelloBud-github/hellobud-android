package com.mediakita.kuis

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    var username: String? = null,
    var email: String? = null,
    var isLoggedin: Boolean = false
) : Parcelable