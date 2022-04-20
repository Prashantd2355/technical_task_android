package com.sliide.usermanagement.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize


@Parcelize
@Keep
data class UsersItem(
    val id: Int?,
    val name: String?,
    val email: String?,
    val gender: String?,
    val status: String?,
) : Parcelable