package com.jasonoh.talkclonefromhawltalk20210803

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class MyContract: ActivityResultContract<Intent, String>() {

    private val PICK_IMAGE = 99

    override fun createIntent(context: Context, input: Intent?): Intent {
        return input!!
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return when(resultCode){
            Activity.RESULT_OK -> {
                if(intent?.getIntExtra("CallType", 0) == PICK_IMAGE){
                    "asd"
                } else ""

            }
            else -> {""}
        }

    }
}