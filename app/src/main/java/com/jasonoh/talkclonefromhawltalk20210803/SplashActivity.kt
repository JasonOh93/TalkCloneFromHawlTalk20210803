package com.jasonoh.talkclonefromhawltalk20210803

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.jasonoh.talkclonefromhawltalk20210803.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var mBinding: ActivitySplashBinding

    private val binding get() = mBinding

    private lateinit var remoteConfig: FirebaseRemoteConfig

    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        todo :: WindowManager.LayoutParams.FLAG_FULLSCREEN -> deprecated
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)window.insetsController?.hide(WindowInsets.Type.statusBars())
        else window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
                displayMessage()
            }
    }

    fun displayMessage(){
        var splashBackground = remoteConfig.getString("splash_background")
        var splashMessageCaps = remoteConfig.getBoolean("splash_message_caps")
        var splashMessage = remoteConfig.getString("splash_message")

        binding.splashLinear.setBackgroundColor(Color.parseColor(splashBackground))
        if(splashMessageCaps){
            AlertDialog.Builder(this).setMessage(splashMessage).setPositiveButton("확인") { dialog, which ->
                finish()
            }.create().show()
        }else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}