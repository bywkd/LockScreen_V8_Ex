package com.jww.lockscreenv8ex

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jww.lockscreenv8ex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "락스크린을 하기 위해서는 이 권한이 필요합니다.", Toast.LENGTH_LONG).show()
            } else {
                startLockScreenService()
            }
        }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bind()
        } else {
            Toast.makeText(this, "안드로이드 8 버전 미만 버전은 다른 예제 코드가 있습니다.", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bind() {
        binding.btnLockScreen.setOnClickListener {
            checkPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val uri = Uri.fromParts("package", packageName, null)
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri)
            permissionLauncher.launch(intent)
        } else {
            startLockScreenService()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startLockScreenService() {
        val intent = Intent(this, LockScreenService::class.java)
        startForegroundService(intent)
    }
}