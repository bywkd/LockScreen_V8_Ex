package com.jww.lockscreenv8ex

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jww.lockscreenv8ex.databinding.ActivityLockscreenBinding

class LockScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLockscreenBinding

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScreen()
    }

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    private fun initScreen() {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(this, null)
    }
}