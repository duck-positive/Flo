package com.example.flo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.HomeMain2Fragment
import com.example.flo.HomeMain3Fragment
import com.example.flo.HomeMain4Fragment
import com.example.flo.HomeMainFragment

class HomeMainViewpagerAdapter (fragment : Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeMainFragment()
            1 -> HomeMain2Fragment()
            2 -> HomeMain3Fragment()
            else -> HomeMain4Fragment()
        }
    }
}