package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumRetrofitInterface {
    @GET("/albums")
    fun getAlbums(): Call<AlbumResponse>
}