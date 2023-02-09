package com.example.flo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentHomeMainBinding
import com.example.flo.databinding.FragmentSaveBinding

class HomeMainFragment : Fragment() {
    lateinit var binding : FragmentHomeMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainBinding.inflate(inflater,container,false)
        return binding.root
    }
}