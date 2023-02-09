package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.adapter.AlbumViewpagerAdapter
import com.example.flo.data.Album
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.example.flo.viewmodel.HomeViewModel

class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    private var gson : Gson = Gson()
    val information = arrayListOf("수록곡","상세정보","영상")

    private var isLiked : Boolean = false
    private val albumViewModel : HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        // Home에서 넘어온 데이터 받아오기
        Log.d("Album","${albumViewModel.selectedAlbum}")
        binding.albumAlbumTitleTv.text = albumViewModel.selectedAlbum.title
        binding.albumAlbumSingerTv.text = albumViewModel.selectedAlbum.singer
        //val albumData = arguments?.getString("album")
        //Log.d("Album", "${albumData}")
        //val album = gson.fromJson(albumData, Album::class.java)
        //isLiked = isLikedAlbum(album.id)

        // Home에서 넘어온 데이터를 반영
        //setViews(album)
        //setClickListeners(album)

        //val songs = getSongs(album.id)

        binding.albumExitIb.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()

        }

        val albumAdapter = AlbumViewpagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTl, binding.albumContentVp){
            tab, position->
            tab.text = information[position]

        }.attach()


        return binding.root
    }

    private fun setViews(album: Album) {
        binding.albumAlbumTitleTv.text = album.title.toString()
        binding.albumAlbumSingerTv.text = album.singer.toString()
        binding.albumAlbumIv.setImageResource(album.coverImg!!)

        if(isLiked){
            binding.albumLikeOffIb.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumLikeOffIb.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setClickListeners(album : Album){
        val userId : Int = getUserIdx((requireContext()))

        binding.albumLikeOffIb.setOnClickListener {
            if(isLiked){
                binding.albumLikeOffIb.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(userId, album.id)
            } else {
                binding.albumLikeOffIb.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }
    private fun likeAlbum(userId : Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId : Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getUserIdx(requireContext())
        val likeId = songDB.albumDao().isLikeAlbum(userId, albumId)

        return likeId != null
    }

    private fun disLikedAlbum(userId: Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt", 0)
    }


    private fun getSongs(albumIdx: Int): ArrayList<Song>{
        val songDB = SongDatabase.getInstance(requireContext())!!

        val songs = songDB.songDao().getSongsInAlbum(albumIdx) as ArrayList

        return songs
    }

}