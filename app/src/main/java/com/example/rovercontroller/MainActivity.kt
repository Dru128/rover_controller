package com.example.rovercontroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.rovercontroller.DelayedPrinter.printText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
{
    val firebase = FirebaseDatabase() // класс для работы с БД
    val output_text by lazy { findViewById<TextView>(R.id.output_text) }
    val right_speed_text by lazy { findViewById<TextView>(R.id.right_speed_text) }
    val left_speed_text by lazy { findViewById<TextView>(R.id.left_speed_text) }
    val right_seekBar by lazy { findViewById<SeekBar>(R.id.right_seekBar) }
    val left_seekBar by lazy { findViewById<SeekBar>(R.id.left_seekBar) }
    val battery by lazy { findViewById<ProgressBar>(R.id.battery) }
    val battery_text by lazy { findViewById<TextView>(R.id.battery_text) }
    val data_time_text by lazy { findViewById<TextView>(R.id.data_time_text) }



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
            setDataTime()
                handler.postDelayed(this, 1000)
            }
        }, 1000)


        setDataTime()
        setButtery(80)
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.emergency_stop_button)
                .setOnClickListener { emergency_stop() }
        val left_scrollView = findViewById<ScrollView>(R.id.left_scrollView)
        output_text.doOnTextChanged { _, _, _, _ -> left_scrollView.scrollTo(0, left_scrollView.bottom) }

        right_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val speed = progress - 100
                right_speed_text.text = speed.toString()
                when (speed) {
                    in 1..100 -> {
                        firebase.setCommand(getString(R.string.right_forward), "true")
                        firebase.setCommand(getString(R.string.right_back), "false")
                    }
                    0 -> {
                        firebase.setCommand(getString(R.string.right_forward), "false")
                        firebase.setCommand(getString(R.string.right_back), "false")
                    }
                    in -100..-1 -> {
                        firebase.setCommand(getString(R.string.right_forward), "false")
                        firebase.setCommand(getString(R.string.right_back), "true")
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        left_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val speed = progress - 100
                left_speed_text.text = speed.toString()
                when (speed) {
                    in 1..100 -> {
                        firebase.setCommand(getString(R.string.left_forward), "true")
                        firebase.setCommand(getString(R.string.left_back), "false")
                    }
                    0 -> {
                        firebase.setCommand(getString(R.string.left_forward), "false")
                        firebase.setCommand(getString(R.string.left_back), "false")
                    }
                    in -100..-1 -> {
                        firebase.setCommand(getString(R.string.left_forward), "false")
                        firebase.setCommand(getString(R.string.left_back), "true")
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    private fun  setDataTime()
    {
        val currentDate = Date()
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val timeText: String = timeFormat.format(currentDate)

        data_time_text.text = "$dateText $timeText"
    }

    private fun setButtery(value: Int)
    {
        if (value in 0..100)
        {
            battery_text.text = "$value%"
            battery.progress = value
        }
    }

    private fun emergency_stop()
    {
        right_seekBar.progress = 100
        left_seekBar.progress = 100
        firebase.stopRover(this)
        printConsole("экстренная остановка")
    }

    private fun printConsole(message: String)
    {
        val word = DelayedPrinter.Word(120, "$message \n")
        word.offset = 50
        printText(word, output_text)
    }

    @SuppressLint("ClickableViewAccessibility")
    var touchListener = View.OnTouchListener { v, event ->
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            val data: String = "true"
           /* when (v.id) {
                button_left_forward.id -> {
                    button_left_back.isEnabled = false
                    firebase.setCommand(getString(R.string.left_forward), data)
                }
                button_left_back.id -> {
                    button_left_forward.isEnabled = false
                    firebase.setCommand(getString(R.string.left_back), data)
                }
                button_right_forward.id -> {
                    button_right_back.isEnabled = false
                    firebase.setCommand(getString(R.string.right_forward), data)
                }
                button_right_back.id -> {
                    button_right_forward.isEnabled = false
                    firebase.setCommand(getString(R.string.right_back), data)
                }
            }
        }
        else if (action == MotionEvent.ACTION_UP)
        {
            val data: String = "false"
            when (v.id) {
                button_left_back.id -> {
                    Toast.makeText(this, "left", Toast.LENGTH_SHORT).show()
                    button_left_forward.isEnabled = true
                    firebase.setCommand(getString(R.string.left_back), data)
                }
                button_left_forward.id -> {
                    button_left_back.isEnabled = true
                    firebase.setCommand(getString(R.string.left_forward), data)
                }
                button_right_forward.id -> {
                    button_right_back.isEnabled = true
                    firebase.setCommand(getString(R.string.right_forward), data)
                }
                button_right_back.id -> {
                    button_right_forward.isEnabled = true
                    firebase.setCommand(getString(R.string.right_back), data)
                }
            }*/
        }
        false
    }
}