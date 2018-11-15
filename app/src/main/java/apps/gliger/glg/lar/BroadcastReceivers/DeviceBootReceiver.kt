package apps.gliger.glg.lar.BroadcastReceivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

class DeviceBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.action == "android.intent.action.BOOT_COMPLETED"){
            val sharedPreferences = context!!.getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
            if(sharedPreferences.getBoolean("isReminderSet",false)){
                var hour = sharedPreferences.getInt("reminder_hour",0)
                var minute = sharedPreferences.getInt("reminder_min",0)

                val alarmIntent = Intent(context!!,AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context!!,0,alarmIntent,0)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                var calendar = java.util.Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.set(Calendar.HOUR_OF_DAY,hour)
                calendar.set(Calendar.MINUTE,minute)
                calendar.set(Calendar.SECOND,0)
                if(hour<=12)
                    calendar.set(Calendar.AM_PM,0)
                else
                    calendar.set(Calendar.AM_PM,1)

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent)
            }
        }
    }
}