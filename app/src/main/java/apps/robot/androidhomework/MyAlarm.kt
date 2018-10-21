package apps.robot.androidhomework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.support.v4.app.NotificationCompat

private const val CHANNEL_ID: String = "WAKE_UP"

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context?.getString(R.string.channel_name) as CharSequence
            val description = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager = context.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(context, CHANNEL_ID).build()

            notificationManager.notify(0, notification)
        } else {
            val builder = NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setContentTitle(context?.resources?.getString(R.string.app_name))
                    .setContentText("Time to wake up!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(Settings.System.DEFAULT_RINGTONE_URI)

            val pendingIntent = PendingIntent.getActivity(context, 0,
                    Intent(context, WakeUpActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

            builder.setContentIntent(pendingIntent)

            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, builder.build())
        }
    }
}