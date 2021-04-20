package com.example.rovercontroller

import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.rovercontroller.DelayedPrinter.printText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

//  https://github.com/Dru128/rover_controller Шайдуров Андрей

class MainActivity : AppCompatActivity()
{
    val output_text by lazy { findViewById<TextView>(R.id.output_text) }
    val right_speed_text by lazy { findViewById<TextView>(R.id.right_speed_text) }
    val left_speed_text by lazy { findViewById<TextView>(R.id.left_speed_text) }
    val right_seekBar by lazy { findViewById<SeekBar>(R.id.right_seekBar) }
    val left_seekBar by lazy { findViewById<SeekBar>(R.id.left_seekBar) }
    val battery by lazy { findViewById<ProgressBar>(R.id.battery) }
    val battery_text by lazy { findViewById<TextView>(R.id.battery_text) }
    val data_time_text by lazy { findViewById<TextView>(R.id.data_time_text) }
    val auto_commit_switch by lazy { findViewById<androidx.appcompat.widget.SwitchCompat>(R.id.auto_commit_switch) }



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printConsole("подключение... \n успех")
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
                when (speed) { //   off/on motor
                    in 1..100 -> {

                    }
                    0 -> {

                    }
                    in -100..-1 -> {

                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {
                if (!auto_commit_switch.isChecked) right_seekBar.progress = 100
            }
        })

        left_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val speed = progress - 100
                left_speed_text.text = speed.toString()
                when (speed) { //   off/on motor
                    in 1..100 -> {

                    }
                    0 -> {

                    }
                    in -100..-1 -> {

                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {
                if (!auto_commit_switch.isChecked) left_seekBar.progress = 100
            }
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
        printConsole("экстренная остановка")
    }

    private fun printConsole(message: String)
    {
        val word = DelayedPrinter.Word(120, "$message \n")
        word.offset = 50
        printText(word, output_text)
    }
}