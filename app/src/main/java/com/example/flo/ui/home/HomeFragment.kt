package com.example.flo.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.*
import com.example.flo.adapter.AlbumRVAdapter
import com.example.flo.adapter.BannerViewpagerAdapter
import com.example.flo.adapter.HomeMainViewpagerAdapter
import com.example.flo.data.Album
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlin.collections.ArrayList
import android.util.Log
import androidx.fragment.app.activityViewModels

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val information = arrayListOf("","","","")
    private var albums = ArrayList<Album>()
    private val homeViewModel : HomeViewModel by activityViewModels()
    private lateinit var songDB: SongDatabase
    //private lateinit var albumRVAdapter: AlbumRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.initAlbum()
        homeViewModel.addAlbum(Album(2,"dasd","das",null,null))
        homeViewModel.addAlbum(Album(1,"DASd","asda",null,null))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.d("home", "${homeViewModel.albumList}")
        //songDB = SongDatabase.getInstance(requireContext())!!
        //albums.addAll(songDB.albumDao().getAlbums())

        binding.homeTodayRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val albumRecyclerViewAdapter = AlbumRVAdapter(homeViewModel.albumList)
        binding.homeTodayRecyclerView.adapter = albumRecyclerViewAdapter
        albumRecyclerViewAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
                Log.d("home_Fragment", "change")
            }
            override fun onRemoveAlbum(position : Int) {
                albumRecyclerViewAdapter.removeItem(position)
            }
        })



       // 배너
        val bannerAdapter = BannerViewpagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))


        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        val homeMainAdapter = HomeMainViewpagerAdapter(this)
        binding.homeMainContentVp.adapter = homeMainAdapter
        TabLayoutMediator(binding.homeMainContentTl, binding.homeMainContentVp){
                tab, position->
            tab.text = information[position]

        }.attach()
        return binding.root
    }
    fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    homeViewModel.selectAlbum(album)
                    val albumJson = Gson().toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
//    private fun initRecyclerView(){
//        albumRVAdapter = AlbumRVAdapter(requireContext())
//        binding.mainPodcastRecyclerView.adapter = albumRVAdapter
//    }
//
//    private fun getAlbums(){
//        val albumService = AlbumService()
//        albumService.setAlbumView(this)
//
//        albumService.getAlbums()
//    }
//
//    override fun onGetAlbumsLoading() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onGetAlbumsSuccess(albums: ArrayList<Album>) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onGetAlbumsFailure(code: Int, message: String) {
//        TODO("Not yet implemented")
//    }


}