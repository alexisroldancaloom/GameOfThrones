package com.caloomboom.practica2.model

import com.google.gson.annotations.SerializedName

data class Personaje(
    var id: Int?,
    var firstName:String?,
    var lastName:String?,
    var fullName:String?,
    var title:String?,
    var family:String?,
    var imageUrl:String?,
)
