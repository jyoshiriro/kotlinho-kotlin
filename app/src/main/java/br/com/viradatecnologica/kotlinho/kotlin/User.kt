package br.com.viradatecnologica.kotlinho.kotlin

data class User (
    var id:Integer? = null,
    var name:String? = null,
    var username:String? = null,
    var email:String? = null,

    var address:Address? = null
)

data class Address (
    var street:String? = null,
    var city:String? = null,

    var geo:Geo? = null
)

data class Geo (
    var lat:Double? = null,
    var lng:Double? = null
)