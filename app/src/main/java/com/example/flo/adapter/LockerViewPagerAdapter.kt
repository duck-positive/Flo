package com.example.flo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.FileFragment
import com.example.flo.SaveFragment
import com.example.flo.SavedAlbumFragment

class LockerViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> SaveFragment()
           1 -> FileFragment()
           else -> SavedAlbumFragment()
       }
    }
}