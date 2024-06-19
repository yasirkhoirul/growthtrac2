package com.example.growthtrack.api

import com.google.gson.annotations.SerializedName

data class ResetRespon(

	@field:SerializedName("msg")
	val msg: String? = null
)

data class Reset(
	val Email:String
)
