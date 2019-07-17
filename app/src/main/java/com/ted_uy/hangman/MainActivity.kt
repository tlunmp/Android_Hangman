package com.ted_uy.hangman

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import java.util.Calendar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log
import android.os.SystemClock


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap: Bitmap = Bitmap.createBitmap(1000, 2000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable

        // rectangle positions
        var left = 425
        var top = 80
        var right = 625
        var bottom = 400

        // draw oval shape to canvas
        shapeDrawable = ShapeDrawable(OvalShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009191"))
        shapeDrawable.draw(canvas)

        // body position
        left = 480
        top = 400
        right = 580
        bottom = 800


        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
        shapeDrawable.draw(canvas)


        // left arm
        left = 320
        top = 400
        right = 490
        bottom = 500


        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
        shapeDrawable.draw(canvas)


        // right arm
        left = 580
        top = 400
        right = 750
        bottom = 500


        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
        shapeDrawable.draw(canvas)


        // left leg position
        left = 410
        top = 700
        right = 480
        bottom = 1050


        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("blue"))
        shapeDrawable.draw(canvas)

        // right leg position
        left = 580
        top = 700
        right = 650
        bottom = 1050


        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( left, top, right, bottom)
        shapeDrawable.getPaint().setColor(Color.parseColor("blue"))
        shapeDrawable.draw(canvas)





        // set bitmap as background to ImageView
        imageView.background = BitmapDrawable(getResources(), bitmap)


        startButton.setOnClickListener {

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)

        }
    }


    override fun onResume() {
        super.onResume()
        val intentAlarm = Intent(this, AlarmReceiver::class.java)
        intentAlarm.putExtra("something", "off")
        println("calling Alarm receiver ")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //set the notification to repeat every fifteen minutes
        val startTime = (1 * 60 * 1000).toLong() // 2 min
        // set unique id to the pending item, so we can call it when needed
        val pi = PendingIntent.getBroadcast(this, ALARM_ID, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi
        )
    }
    override fun onStop() {
        super.onStop()

        val intentAlarm = Intent(this, AlarmReceiver::class.java)
        intentAlarm.putExtra("something", "on")
        println("calling Alarm receiver ")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //set the notification to repeat every fifteen minutes
        val startTime = (1 * 60 * 1000).toLong() // 2 min
        // set unique id to the pending item, so we can call it when needed
        val pi = PendingIntent.getBroadcast(this, ALARM_ID, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi
        )
    }

    companion object {
        val ALARM_ID = 12345
    }
}
