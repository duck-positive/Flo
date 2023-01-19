package com.example.flo.repository

import android.util.Log
import com.example.flo.LookView
import com.example.flo.SongResponse
import com.example.flo.SongRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SongService {
    private lateinit var lookView : LookView

    fun setLookView(lookView : LookView){
        this.lookView = lookView
    }

    fun getSongs() {
        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
            GsonConverterFactory.create()).build()
        val songService = retrofit.create(SongRetrofitInterface::class.java)

        lookView.onGetSongsLoading()

        songService.getSongs().enqueue(object : Callback<SongResponse> {
            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {
                Log.d("GETSONGS_API_SUCCESS", response.toString())

                val resp = response.body()!!

                when (resp.code) {
                    1000 -> lookView.onGetSongsSuccess(resp.result!!.songs)
                    else -> lookView.onGetSongsFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                Log.d("GETSONGS_API_FAILURE", t.message.toString())

                lookView.onGetSongsFailure(400, "네트워크 오류 발생")
            }
        })
    }
}