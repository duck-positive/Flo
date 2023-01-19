package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.flo.adapter.SongRVAdapter
import com.example.flo.databinding.FragmentLookBinding
import com.example.flo.repository.SongService


class LookFragment : Fragment(), LookView {

    private lateinit var binding: FragmentLookBinding
    private lateinit var songRVAdapter: SongRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initRecyclerView()
        getSongs()
    }
    private fun initRecyclerView(){
        songRVAdapter = SongRVAdapter(requireContext())
        binding.lookFloChartRv.adapter = songRVAdapter
    }

    private fun getSongs(){
        val songService = SongService()
        songService.setLookView(this)

        songService.getSongs()
    }

    override fun onGetSongsLoading() {
        //로딩바
    }

    override fun onGetSongsSuccess(songs: ArrayList<Song>) {
        songRVAdapter.addSongs(songs)
    }

    override fun onGetSongsFailure(code: Int, message: String) {
        when(code){
            400 -> Log.d("LOOKFRAGMENT_FAILURE", message)
        }
    }

}