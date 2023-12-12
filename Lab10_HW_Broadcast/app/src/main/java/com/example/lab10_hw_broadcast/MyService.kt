package com.example.lab10_hw_broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class MyService : Service() {

    private var channel = ""
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //解析 Intent 取得字串訊息
        intent?.extras?.let {
            channel = it.getString("channel", "")
        }
        broadcast(
            when(channel) {
                "music" -> "歡迎來到音樂頻道"
                "new" -> "歡迎來到新聞頻道"
                "sport" -> "歡迎來到體育頻道"
                else -> "頻道錯誤"
            }
        )
        //若 thread 被初始化過且正在運行，則中斷它
        CoroutineScope(Dispatchers.IO).coroutineContext[Job]?.cancel()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(3000) // Delay for three seconds
                broadcast(
                    when (channel) {
                        "music" -> "即將播放本月 TOP10 音樂"
                        "new" -> "即將為您提供獨家新聞"
                        "sport" -> "即將播報本週 NBA 賽事"
                        else -> "頻道錯誤"
                    }
                )
            } catch (e: CancellationException) {
                e.printStackTrace()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun broadcast(msg: String) =
        sendBroadcast(Intent(channel).putExtra("msg", msg))
}