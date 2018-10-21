package apps.robot.androidhomework

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_set_alarm.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        time_picker.hour,
                        time_picker.minute,
                        0)
            } else {
                calendar.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        time_picker.currentHour,
                        time_picker.currentMinute,
                        0)
            }
            setAlarm(calendar.timeInMillis)
        }

        btn_stop_alarm.setOnClickListener {
            stopAlarm()
        }
    }

    private fun stopAlarm() {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.cancel(pendingIntent)
    }

    private fun setAlarm(timeInMills: Long) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMills, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}
