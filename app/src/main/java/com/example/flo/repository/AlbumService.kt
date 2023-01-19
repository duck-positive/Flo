package com.example.flo.repository

import android.util.Log
import com.example.flo.AlbumResponse
import com.example.flo.AlbumRetrofitInterface
import com.example.flo.AlbumView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumService {
    private lateinit var albumView : AlbumView

    fun setAlbumView(albumView : AlbumView){
        this.albumView = albumView
    }

    fun getAlbums() {
        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
            GsonConverterFactory.create()).build()
        val albumService = retrofit.create(AlbumRetrofitInterface::class.java)

        albumView.onGetAlbumsLoading()

        albumService.getAlbums().enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>, response: Response<AlbumResponse>) {
                Log.d("GETSONGS_API_SUCCESS", response.toString())

                val resp = response.body()!!

                when (resp.code) {
                    1000 -> albumView.onGetAlbumsSuccess(resp.result!!.albums)
                    else -> albumView.onGetAlbumsFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AlbumResponse>, t: Throwable) {
                Log.d("GETSONGS_API_FAILURE", t.message.toString())

                albumView.onGetAlbumsFailure(400, "네트워크 오류 발생")
            }
        })
    }
}
