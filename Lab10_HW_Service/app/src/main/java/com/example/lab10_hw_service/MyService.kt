package com.example.lab10_hw_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(5000)
                val intent = Intent(this@MyService, MainActivity2::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                this@MyService.startActivity(intent)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.IO).cancel()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        throw UnsupportedOperationException("Not yet implemented")
    }
}