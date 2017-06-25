package br.com.viradatecnologica.kotlinho.kotlin

import feign.Param;
import feign.RequestLine;


interface UserRequets {

    @RequestLine("GET /users/{id}")
    fun getUser(@Param("id") id: Int?): User

}
