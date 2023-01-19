package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.adapter.LockerViewPagerAdapter
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private var gson : Gson = Gson()
    val information = arrayListOf("저장한 곡","음악파일", "저장한 앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerViewPagerAdapter(this)

        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTl, binding.lockerContentVp){
                tab, position->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        val jwt = getUserIdx(requireContext())
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerNameTv.visibility = View.GONE
            binding.lockerLoginTv.setOnClickListener{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        } else {
            binding.lockerLoginTv.text = "로그아웃"
            val userDB = SongDatabase.getInstance(requireContext())!!
            val user = userDB.userDao().getName(jwt)
            binding.lockerNameTv.text = user?.name
            binding.lockerNameTv.visibility = View.VISIBLE
            binding.lockerLoginTv.setOnClickListener{
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }
    private fun getJwt() : Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

        return spf!!.getInt("jwt", 0)
    }
    private fun logout() {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()

        editor.remove("jwt")
        editor.apply()
    }


}