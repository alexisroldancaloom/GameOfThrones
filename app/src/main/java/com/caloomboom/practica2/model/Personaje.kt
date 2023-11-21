package com.caloomboom.practica2.model

import com.google.gson.annotations.SerializedName

data class Personaje(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("firstName")
    var firstName:String?,
    @SerializedName("lastName")
    var lastName:String?,
    @SerializedName("fullName")
    var fullName:String?,
    @SerializedName("title")
    var title:String?,
    @SerializedName("family")
    var family:String?,
    @SerializedName("imageUrl")
    var imageUrl:String?,
)
