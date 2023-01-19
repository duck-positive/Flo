package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignupBinding
import com.example.flo.repository.AuthService

class SignUpActivity : AppCompatActivity(), SignUpView {

    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupSignUpTv.setOnClickListener {
            signUp()
            finish()
        }
    }
    private fun getUser() : User {
        val email : String = binding.signupIdEt.text.toString() + "@" + binding.signupEmailEt.text.toString()
        val name : String = binding.signupNameEt.text.toString()
        val pwd : String = binding.signupPwEt.text.toString()

        return User(email, pwd, name)
    }

//    private fun signUp(){
//        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
//            SampleToast.createToast(this, "아이디 형식이 잘못되었습니다.")?.show()
//            return
//        }
//        if(binding.signupPwEt.text.toString() != binding.signupPwCheckEt.text.toString() || binding.signupPwEt.text.toString().isEmpty()){
//            SampleToast.createToast(this, "비밀번호 형식이 잘못되었습니다.")?.show()
//            return
//        }
//
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val users = userDB.userDao().getUsers()
//        Log.d("Signup", users.toString())
//    }
    private fun signUp(){
        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
            SampleToast.createToast(this, "아이디 형식이 잘못되었습니다.")?.show()
            return
        }

        if(binding.signupNameEt.text.toString().isEmpty()){
            SampleToast.createToast(this, "이름을 넣어주세요")?.show()
            return
        }
        if(binding.signupPwEt.text.toString() != binding.signupPwCheckEt.text.toString() || binding.signupPwEt.text.toString().isEmpty()){
            SampleToast.createToast(this, "비밀번호 형식이 잘못되었습니다.")?.show()
            return
        }
        else {
            val authService = AuthService()
            authService.setSignUpView(this)
            authService.signUp(getUser())
        }
    }

    override fun onSignUpLoading() {
        binding.signupLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.signupLoadingPb.visibility = View.GONE
        // 로그인이 완료 됐을 때 signupactivity 종료
        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.signupLoadingPb.visibility = View.GONE

        when(code){
            2016, 2017 -> {
                binding.signupIdErrorTv.visibility = View.VISIBLE
                binding.signupIdErrorTv.text = message
            }
        }
    }
}