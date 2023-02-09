package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.adapter.SongRVAdapter
import com.example.flo.databinding.FragmentSaveBinding
import java.util.ArrayList

class SaveFragment : Fragment() {
    lateinit var binding : FragmentSaveBinding
    lateinit var songDB: SongDatabase
    lateinit var SongActivity : SongActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaveBinding.inflate(inflater,container,false)
        //songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }
    private fun initRecyclerview(){
        binding.lockerFileRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val songRVAdapter = SongRVAdapter(requireContext())

        songRVAdapter.setMyItemClickListener(object : SongRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                //songDB.songDao().updateIsLikeById(false,songId)
            }
        })


        binding.lockerFileRecyclerView.adapter = songRVAdapter

        //songRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList)

    }

//    private fun bottom(){
//        binding.albumSelectAllIv.setOnClickListener {
//            val bottomSheet = BottomSheetDialog()
//            bottomSheet.show(, bottomSheet.tag)
//        }
//    }


}