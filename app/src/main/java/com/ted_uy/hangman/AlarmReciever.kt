package com.ted_uy.hangman

import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.R
import android.app.Notification
import android.app.NotificationChannel
import androidx.core.app.NotificationCompat
import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent.getIntent
import android.graphics.Color
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class AlarmReceiver : BroadcastReceiver() {
    internal var notificationId = 1

    var CHANNEL_ID = "com.ted_uy.hangman"
    var channel_name = "hangman"
    var chanel_description = "this is description"


    //recieve the alarm from the main activity
    override fun onReceive(context: Context, intent: Intent) {

        var some = intent.extras.getString("something")

        if(some == "on") {

            Toast.makeText(context, "AlarmReceiver", Toast.LENGTH_LONG).show()
            // Gets an instance of the NotificationManager service
            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.arrow_up_float)
                .setContentTitle("Hangman")
                .setContentText("Please come back to the application")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            createNotificationChannel(context)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }
        } else {
            return
        }


    }

    //create notfication channel
    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "hangman"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}