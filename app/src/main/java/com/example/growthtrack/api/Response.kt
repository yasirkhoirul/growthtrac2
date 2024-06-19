package com.example.growthtrack.api

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("babyName")
	val babyName: String? = null,

	@field:SerializedName("predictionsBinary")
	val predictionsBinary: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("breastfeeding")
	val breastfeeding: Int? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
data class Response23Item(

	@field:SerializedName("gists_url")
	val gistsUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("starred_url")
	val starredUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String,

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("events_url")
	val eventsUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("site_admin")
	val siteAdmin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("node_id")
	val nodeId: String,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String
)

data class PlacesResponse(
	val results: List<PlaceResult>
)

data class PlaceResult(
	val name: String,
	val geometry: Geometry
)

data class Geometry(
	val location: Location
)

data class Location(
	val lat: Double,
	val lng: Double
)

data class Hospital(val name: String, val distance: Float)
