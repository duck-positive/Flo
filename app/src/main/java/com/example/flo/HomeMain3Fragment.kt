package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentHomeMain3Binding
import com.example.flo.databinding.FragmentHomeMainBinding
import com.example.flo.databinding.FragmentSaveBinding

class HomeMain3Fragment : Fragment() {
    lateinit var binding : FragmentHomeMain3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMain3Binding.inflate(inflater,container,false)
        return binding.root
    }
}