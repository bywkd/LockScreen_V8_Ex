package com.jww.lockscreenv8ex

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class LockScreenService : Service() {
    companion object {
        const val LOCKSCREEN_ID = "lockScreen_id"
        const val LOCKSCREEN_CODE = 5000
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    val newIntent = Intent(context, LockScreenActivity::class.java)
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(newIntent)
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(LOCKSCREEN_ID, "락스크린 테스트", NotificationManager.IMPORTANCE_DEFAULT)
        nm.createNotificationChannel(channel)

        val intentNoti = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            LOCKSCREEN_CODE,
            intentNoti,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val notification = Notification.Builder(this, LOCKSCREEN_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("잠금 화면 서비스")
            .setContentIntent(pendingIntent)
            .build()
        startForeground(LOCKSCREEN_CODE, notification)
        stopForeground(false)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(receiver, filter)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}