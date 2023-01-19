package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.repository.AuthService

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginRegisterTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInTv.setOnClickListener {
            login()
        }
    }
//    private fun login() {
//        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginEmailEt.text.toString().isEmpty()){
//            SampleToast.createToast(this, "아이디가 잘못되었습니다.")?.show()
//            return
//        }
//        if(binding.loginPwEt.text.toString().isEmpty()){
//            SampleToast.createToast(this, "비밀번호가 잘못되었습니다.")?.show()
//            return
//        }
//
//        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginEmailEt.text.toString()
//        val pwd : String = binding.loginPwEt.text.toString()
//
//        val songDB = SongDatabase.getInstance(this)!!
//
//        val user = songDB.userDao().getUser(email, pwd)
//        if(user==null){
//            SampleToast.createToast(this, "계정이 존재하지 않습니다.")?.show()
//        }else {
//            user?.let {
//                Log.d("loginget", "userId : ${user.id}, $user")
//                saveJwt(user.id)
//                startMainActivity()
//            }
//        }
//    }

    private fun login() {
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginEmailEt.text.toString().isEmpty()){
            SampleToast.createToast(this, "아이디가 잘못되었습니다.")?.show()
            return
        }
        if(binding.loginPwEt.text.toString().isEmpty()){
            SampleToast.createToast(this, "비밀번호가 잘못되었습니다.")?.show()
            return
        }

        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginEmailEt.text.toString()
        val pwd : String = binding.loginPwEt.text.toString()
        val user = User(email, pwd,"")

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(user)

    }
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

//    private fun saveJwt(jwt: Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt", jwt)
//        editor.apply()
//    }

    override fun onLoginLoading() {
        binding.loginLoadingPb.visibility = View.VISIBLE
    }

    override fun onLoginSuccess(auth : Auth) {
        binding.loginLoadingPb.visibility = View.GONE
        Log.d("login-success","로그인 성공")
        saveJwt(this, auth.jwt)
        saveUserIdx(this, auth.userIdx)
        startMainActivity()
    }

    override fun onLoginFailure(code : Int, message: String) {
        binding.loginLoadingPb.visibility = View.GONE

        when(code){
            2015, 2019, 3014 -> {
                binding.loginErrorTv.visibility = View.VISIBLE
                binding.loginErrorTv.text = message
            }
        }
    }
}