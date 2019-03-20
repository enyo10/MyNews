package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;


import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.NotificationAlarmReceiver;

public class NotificationsActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.checkBox_arts)
    CheckBox mCheckBox_arts;
    @BindView(R.id.checkBox_business)
    CheckBox mCheckBox_business;
    @BindView(R.id.checkBox_politics)
    CheckBox mCheckBox_politics;
    @BindView(R.id.checkBox_sports)
    CheckBox mCheckBox_sports;
    @BindView(R.id.checkBox_travel)
    CheckBox mCheckBox_travel;
    @BindView(R.id.checkBox_entrepreneurs)
    CheckBox mCheckBox_entrepreneurs;
    @BindView(R.id.activity_notifications_switch)
    Switch mSwitch;
    // check current state of a Switch (true or false).
  //  Boolean switchState = mSwitch.isChecked();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        configureToolBar();
        scheduleAlarm();
    }

    /**
     * This method to configure the toolbar.
     */
    public void configureToolBar(){

        mToolbar.setTitle("Notifications");
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    /**
     * This class to schedule the alarm that will fire the notification.
     */
    public void scheduleAlarm()
    {
        // set alarm to wakeup the device at 24 o'clock.

       /* Calendar calendar =Calendar.getInstance ();
        calendar.setTimeInMillis (System.currentTimeMillis ());
        calendar.set(Calendar.HOUR_OF_DAY,0);
        AlarmManager alarmManager=(AlarmManager) getSystemService (Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent   alarm_Intent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating (AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis (),AlarmManager.INTERVAL_DAY,alarm_Intent);

       */

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationAlarmReceiver.class);

        PendingIntent   alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // SetRepeating() lets you specify a precise custom interval--in this case, 2 minutes.

        Long time = new GregorianCalendar().getTimeInMillis()+2*60*1000;

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, time,
                1000 * 60 * 2, alarmIntent);
        Toast.makeText(this, "Alarm Scheduled for 2 min", Toast.LENGTH_LONG).show();

    }


}
