package com.caloomboom.practica2.model

import com.google.gson.annotations.SerializedName

data class Personajes(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("fullName")
    var fullName:String?,
    @SerializedName("title")
    var title:String?,
    @SerializedName("imageUrl")
    var imageUrl:String?,
    @SerializedName("image")
    var image:String?




)
