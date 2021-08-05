package com.jasonoh.talkclonefromhawltalk20210803

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.FirebaseAuthKtxRegistrar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.jasonoh.talkclonefromhawltalk20210803.databinding.ActivitySignupBinding
import com.jasonoh.talkclonefromhawltalk20210803.model.UserModel

class SignupActivity : AppCompatActivity() {

    lateinit var mBinding: ActivitySignupBinding
    private val binding get() = mBinding

    private lateinit var splashBackground : String

    private lateinit var imageUri: Uri

    var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteConfig = Firebase.remoteConfig
        splashBackground = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splashBackground)

        launcher = registerForActivityResult(MyContract()){ result: String? ->
            result?.let{
//                firebaseAuthWithGoogle(it)  //tokenId를 이용해 firebase에 인증하는 함수 호출.
            }
        }

        binding.ivSignupActivityProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            //
        }

        binding.btnSignupActivitySignup.setBackgroundColor(Color.parseColor(splashBackground))
        binding.btnSignupActivitySignup.setOnClickListener {

            if(binding.etSignupActivityEmail.text.toString() == ""
                || binding.etSignupActivityName.text.toString() == ""
                || binding.etSignupActivityPassword.text.toString() == "") return@setOnClickListener

            Firebase.auth
                .createUserWithEmailAndPassword(binding.etSignupActivityEmail.text.toString(), binding.etSignupActivityPassword.text.toString())
                .addOnCompleteListener(this@SignupActivity) {
                    val userModel = UserModel()
                    userModel.userName = binding.etSignupActivityName.text.toString()
                    val uid = it.result.user?.uid

                    Log.e("TAG", "SignupActivity -> onCreate: ${Firebase.database.reference.toString()}", )

                    // todo :: 실시간 데이터베이스 위치 설정에서 오류가 나옴.. 그부분 임시로 database의 url을 지정함함
                   Firebase.database("https://talkclonefromhawltalk20210803-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("users").child(uid!!).setValue(userModel)
                }
        }
    }// onCreate method
}// SignupActivity class