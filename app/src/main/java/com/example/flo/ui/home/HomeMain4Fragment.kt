package com.example.flo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.*

class HomeMain4Fragment : Fragment() {
    lateinit var binding : FragmentHomeMain4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMain4Binding.inflate(inflater,container,false)
        return binding.root
    }
}