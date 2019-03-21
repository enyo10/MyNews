package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import org.json.JSONException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
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
    private Filters mFilters;
    private SharedPreferences mSharedPreferences;
    public static final String NOTIFICATION_FILTER_KEY = NotificationsActivity.class.getName();
    private static final String NOTIFICATION_SETTING="NOTIFICATION_BUTTON_SET";
    private boolean isNotificationSet=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);

       Map<String,String>map= getSearchSetting();


        mFilters=new Filters();
        configureToolBar();

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

    @OnCheckedChanged(R.id.activity_notifications_switch)
    void onNotificationSet(CompoundButton button, boolean checked) {
        if(checked){
            Log.i("TAG","Switch button is checked");
            if(mFilters.getFilters().size()!=1){
                Log.i("TAG"," filters: "+ Filters.mapToJsonString(mFilters.getFilters()));
                // Save search data.
                this.saveNotificationsFilter(Filters.mapToJsonString(mFilters.getFilters()));
                scheduleAlarm();
            } else{
                button.setChecked(false);
                Toast.makeText(this, " One category must be set.", Toast.LENGTH_LONG).show();

            }
        }
        else {
            Log.i("TAG","Switch button is not set.");

        }

    }

    /**
     * Add selected category to the filter map.
     * @param v,
     *       the view selected.
     */
    public void onCheckBoxClicked(View v) {
        // Is the view now checked?
        boolean checked = ((CheckBox) v).isChecked();

        // Check which checkbox was clicked
        switch (v.getId()) {
            case R.id.checkBox_arts:
                if (checked)
                    // Put value on filters
                    mFilters.addSelectedCategory("Arts");
                else
                    // Remove the remove the value
                    mFilters.removeSelectedCategory("Arts");
                break;
            case R.id.checkBox_business:
                if (checked)
                    mFilters.addSelectedCategory("Business");
                else
                    mFilters.removeSelectedCategory("Business");
                break;
            case R.id.checkBox_entrepreneurs:
                if (checked)
                    // Put value on filters
                    mFilters.addSelectedCategory("Entrepreneurs");
                else
                    // Remove the remove the value
                    mFilters.removeSelectedCategory("Entrepreneurs");
                break;
            case R.id.checkBox_politics:
                if (checked)
                    mFilters.addSelectedCategory("Politics");
                else
                    mFilters.removeSelectedCategory("Politics");
                break;

            case R.id.checkBox_sports:
                if (checked)
                    // Put value on filters
                    mFilters.addSelectedCategory("Sport");
                else
                    // Remove the remove the value
                    mFilters.removeSelectedCategory("Sport");
                break;
            case R.id.checkBox_travel:
                if (checked)
                    mFilters.addSelectedCategory("Travel");
                else
                    mFilters.removeSelectedCategory("Travel");
                break;

        }
    }

    /**
     * Save the search filter to the shared preferences.
     * @param filter,
     *        the filter to save. ( A String).
     */
    protected void saveNotificationsFilter(String filter){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(NOTIFICATION_FILTER_KEY,filter);
        editor.putBoolean(NOTIFICATION_SETTING,isNotificationSet);
        editor.commit();

    }

        /**
         * This class to schedule the alarm that will fire the notification.
         */
        public void scheduleAlarm() {
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

            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            // SetRepeating() lets you specify a precise custom interval--in this case, 2 minutes.

            Long time = new GregorianCalendar().getTimeInMillis() + 2 * 60 * 1000;

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, time,
                    1000 * 60 * 2, alarmIntent);
            Toast.makeText(this, "Alarm Scheduled for 2 min", Toast.LENGTH_LONG).show();

        }

    /**
     * Get retrieve the saved data from shared preferences.
     * @return Map<String,String>,
     */
    protected Map<String,String> getSearchSetting(){
            Map<String,String>map=new HashMap<>();

            String searchString =mSharedPreferences.getString(NOTIFICATION_FILTER_KEY,"");
            if(searchString!=""){
                try {
                    map=Filters.jsonToMap(searchString);
                   Log.i("TAG", " map"+ map.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return map;
        }
        protected void setValues(Map<String,String>mapValues){
            //String searchWord =mapValues.get();


        }




    }


