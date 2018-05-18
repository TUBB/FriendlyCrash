package io.github.tubb.fcrash.sample

import android.annotation.SuppressLint
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
            throw RuntimeException("Crash by yourself!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_crash.setOnClickListener {
            Toast.makeText(this, "App will crash after 5s", Toast.LENGTH_SHORT).show()
            handler.sendEmptyMessageDelayed(0, 5000)
        }
    }
}
