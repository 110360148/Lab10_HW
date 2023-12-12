package com.example.lab10_hw_broadcast_receiver

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    private var h = 0
    private var m = 0
    private var s = 0

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        flag = intent.getBooleanExtra("flag", false)
        CoroutineScope(Dispatchers.IO).launch {
            while (flag) {
                try {
                    delay(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                s++
                if (s >= 60) {
                    m = 0
                    h++
                }
                val i = Intent("MyMessage")
                val bundle = Bundle()
                bundle.putInt("H", h)
                bundle.putInt("M", m)
                bundle.putInt("S", s)
                i.putExtras(bundle)
                sendBroadcast(i)
            }
        }
        return START_STICKY
    }

    companion object {
        var flag = false
    }
}