package io.github.tubb.fcrash.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            throw RuntimeException("Crashed by me!")
        }
    }

    private var bgmFriendlyCrash: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val app: App = application as App
        swt_app.setOnCheckedChangeListener { _, isChecked ->
            app.changeAppFriendly(isChecked)
        }
        swt_bgm.setOnCheckedChangeListener { _, isChecked ->
            bgmFriendlyCrash = isChecked
        }
        btn_crash.setOnClickListener {
            Toast.makeText(this, "App will crash after 5s", Toast.LENGTH_SHORT).show()
            handler.sendEmptyMessageDelayed(0, 5000)
        }
        btn_crash_bg.setOnClickListener {
            Toast.makeText(this, "Background service will crash after 5s", Toast.LENGTH_SHORT).show()
            val bgmIntent: Intent = Intent(this, ProcessService::class.java)
            bgmIntent.putExtra("bgmFriendlyCrash", bgmFriendlyCrash)
            startService(bgmIntent)
        }
    }
}
