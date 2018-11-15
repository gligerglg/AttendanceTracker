package apps.gliger.glg.lar.BroadcastReceivers

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.NotificationCompat
import apps.gliger.glg.lar.Activities.MainMenu
import apps.gliger.glg.lar.R
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    lateinit var sharedPreferences : SharedPreferences

    override fun onReceive(context: Context?, intent: Intent?) {
        sharedPreferences = context!!.getSharedPreferences("lar_shared", Context.MODE_PRIVATE)
        if(!isTodayMarkerd()){
            val intent = Intent(context!!,MainMenu::class.java)
                    .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }
            val pendingIntent = PendingIntent.getActivity(context!!,0,intent,0)
            val builder = NotificationCompat.Builder(context!!,"LAR")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Attendance Marker")
                    .setContentText("It's time to mark today attendance")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)


            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val id = System.currentTimeMillis()/1000
            nManager.notify(id.toInt(),builder.build())
        }
    }

    fun getDateString(): String {
        var cal = Calendar.getInstance().time
        var dtf = SimpleDateFormat("yyyy/MM/dd")
        return dtf.format(cal)
    }

    fun isTodayMarkerd(): Boolean {
        val prev_record = sharedPreferences.getString("today_record", "")
        return prev_record == getDateString()
    }
}