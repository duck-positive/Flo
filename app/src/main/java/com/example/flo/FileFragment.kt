package com.example.flo

import android.graphics.Insets
import android.graphics.Insets.add
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentFileBinding
import com.example.flo.databinding.FragmentSaveBinding
import java.util.ArrayList

class FileFragment : Fragment() {
    lateinit var binding : FragmentFileBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFileBinding.inflate(inflater,container,false)
        return binding.root
    }


}