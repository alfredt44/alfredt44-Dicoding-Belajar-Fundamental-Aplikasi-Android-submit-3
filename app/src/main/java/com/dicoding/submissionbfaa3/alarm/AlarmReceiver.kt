package com.dicoding.submissionbfaa3.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.ui.main.MainActivity

import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIFICATION_CHANNEL_NAME = "Notif Channel"
        const val NOTIFICATION_CHANNEL_ID = "notif-id"
        const val NOTIFICATION_ID = 32
        const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        setNotification(context)
    }

    private fun setNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(MainActivity::class.java)
            .addNextIntent(intent)
            .getPendingIntent(ID_REPEATING, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content_text))
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)


            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)

        }


        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent: PendingIntent =
            Intent(context, AlarmReceiver::class.java).let {
                PendingIntent.getBroadcast(context,
                    ID_REPEATING,
                    it,
                    0)
            }
        val repeatingTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }


        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            repeatingTime.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent)
        Toast.makeText(context, context.getString(R.string.reminder_setup), Toast.LENGTH_SHORT)
            .show()
    }

    fun cancelAlarm(context: Context) {
        val repeatingIntent: PendingIntent =
            Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
            }
        repeatingIntent.cancel()

        Toast.makeText(context, context.getString(R.string.reminder_disabled), Toast.LENGTH_SHORT)
            .show()
    }


}