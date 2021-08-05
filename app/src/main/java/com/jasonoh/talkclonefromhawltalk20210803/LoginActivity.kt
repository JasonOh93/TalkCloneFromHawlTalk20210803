package com.jasonoh.talkclonefromhawltalk20210803

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.jasonoh.talkclonefromhawltalk20210803.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityLoginBinding
    private val binding get() = mBinding

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        remoteConfig = Firebase.remoteConfig
        var splashBackground = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splashBackground)

        binding.btnLoginActivityLogin.setBackgroundColor(Color.parseColor(splashBackground))
        binding.btnLoginActivitySignUp.setBackgroundColor(Color.parseColor(splashBackground))

        binding.btnLoginActivitySignUp.setOnClickListener { startActivity(Intent(this@LoginActivity, SignupActivity::class.java)) }
    }
}