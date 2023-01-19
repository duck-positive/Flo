package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {
    lateinit var binding : FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        binding.albumMyMixOffIb.setOnClickListener {
            setAlbumMix(true)
        }
        binding.albumMyMixOnIb.setOnClickListener {
            setAlbumMix(false)
        }
        return binding.root
    }


    fun setAlbumMix(mixOn: Boolean){
        if(mixOn){
            binding.albumMyMixOffIb.visibility = View.GONE
            binding.albumMyMixOnIb.visibility = View.VISIBLE
        } else{
            binding.albumMyMixOffIb.visibility = View.VISIBLE
            binding.albumMyMixOnIb.visibility = View.GONE
        }
    }
}