package com.example.flo.viewmodel

import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.example.flo.data.Album

class HomeViewModel : ViewModel() {
    private val _albumList = arrayListOf<Album>()
    val albumList : ArrayList<Album>
        get() = _albumList

    private var _selectedAlbum = Album()
    val selectedAlbum : Album
        get() = _selectedAlbum


    fun addAlbum(album: Album){
        _albumList.add(album)
    }
    fun selectAlbum(album : Album) {
        _selectedAlbum = album
    }
    fun initAlbum(){
        _albumList.clear()
    }
}