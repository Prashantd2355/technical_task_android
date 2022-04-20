package com.sliide.usermanagement.models

import androidx.annotation.Keep

@Keep
data class AddUserRequestBody(val name: String, val gender: String, val email: String, val status: String)