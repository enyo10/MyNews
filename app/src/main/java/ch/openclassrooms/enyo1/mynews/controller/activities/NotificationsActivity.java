package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import org.json.JSONException;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
import ch.openclassrooms.enyo1.mynews.utils.NotificationAlarmReceiver;


public class NotificationsActivity extends BaseActivity {

    private static final String TAG = NotificationsActivity.class.getSimpleName();

    @BindView(R.id.activity_notification_words_edit)
    EditText mEditText;
    @BindView(R.id.activity_notifications_switch)
    Switch mSwitchButton;


    private SharedPreferences mSharedPreferences;
    public static final String SHARED_PREFERENCES_KEY="SHARED_PREFERENCES_KEY";

    public static final String SWITCH_BUTTON_STATE_KEY="SWITCH_BUTTON_STATE_KEY";
    public static final String CHECKED_BOXES_KEY="CHECKED_BOX_KEY";
    public static final String SEARCH_TEXT_KEY="SEARCH_TEXT_KEY";

    private boolean isNotificationSet=false;
    private PendingIntent mPendingIntent;
    private Intent mIntent;
   // private String mSearchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureAlarmManager();

        mSharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY,Context.MODE_PRIVATE);
        this.retrieveNotificationSetting();

       configureToolBar();

    }

   @Override
    public void configureToolBar(){
        mToolbar.setTitle("Notifications");
        super.configureToolBar();

    }


    @OnCheckedChanged(R.id.activity_notifications_switch)
    void onNotificationSet(CompoundButton button, boolean checked) {

         if(checked && !isNotificationSet){

            Log.i(TAG," filter size " +mFilters.getSelectedValues().size());
            if(mFilters.getSelectedValues().size()!=0 && !mEditText.getText().toString().equals("")){

                isNotificationSet=true;

                mIntent.putExtra(SEARCH_TEXT_KEY,mEditText.getText().toString());
                mIntent.putExtra(CHECKED_BOXES_KEY,Filters.mapToJsonString(mFilters.getSelectedValues()));
                // Start alarm && set notification to true.
                scheduleAlarm();

            } else{
                button.setChecked(false);
                isNotificationSet=false;

                Toast toast = Toast.makeText(getApplicationContext(),
                        " No search Work or check box is selected.",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        }
        else {
            if(!checked && isNotificationSet){
                isNotificationSet=false;
                this.stopAlarm();
            }

        }

    }

    protected void removeNotificationSettings(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    private void saveNotificationSettings(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(SWITCH_BUTTON_STATE_KEY,isNotificationSet);
        editor.putString(SEARCH_TEXT_KEY,mEditText.getText().toString());
        editor.putString(CHECKED_BOXES_KEY,Filters.mapToJsonString(mFilters.getSelectedValues()));
        editor.apply();
    }


    private void retrieveNotificationSetting(){
        Map<String,String> map=new HashMap<>();
        String mSearchText;

        isNotificationSet = mSharedPreferences.getBoolean(SWITCH_BUTTON_STATE_KEY,false);
        mSwitchButton.setChecked(isNotificationSet);
        Log.i(TAG, " In retrieve : "+ isNotificationSet);

        Log.i(TAG,"actual state : notification setting -> " + mSwitchButton.isChecked());

        mSearchText=mSharedPreferences.getString(SEARCH_TEXT_KEY,"");
        mEditText.setText(mSearchText);

        String selectedValues =mSharedPreferences.getString(CHECKED_BOXES_KEY,"");

        try {
            map=Filters.jsonToMap(selectedValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }

         mFilters.setSelectedValues(map);

        if(mFilters.getSelectedValues().containsKey("Arts")){
            mCheckBox_arts.setChecked(true);
        }

        if(mFilters.getSelectedValues().containsKey("Business"))
            mCheckBox_business.setChecked(true);


        if(mFilters.getSelectedValues().containsKey("Entrepreneurs"))
            mCheckBox_entrepreneurs.setChecked(true);


        if(mFilters.getSelectedValues().containsKey("Politics"))
            mCheckBox_politics.setChecked(true);


        if(mFilters.getSelectedValues().containsKey("Sports"))
            mCheckBox_sports.setChecked(true);


        if(mFilters.getSelectedValues().containsKey("Travel"))
            mCheckBox_travel.setChecked(true);

        Log.i(TAG," selected value "+mFilters.getSelectedValues());
        Log.i(TAG," editTEXt "+mSearchText);
    }

    /**
     * Configure the alarm manager.
     */
    private void configureAlarmManager(){
        mIntent = new Intent(this, NotificationAlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(this, 0,mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * This class to schedule the alarm that will fire the notification.
     */
    public void scheduleAlarm() {
            // set alarm to wakeup the device at 7 o'clock.
        /* Calendar calendar =Calendar.getInstance ();
        calendar.setTimeInMillis (System.currentTimeMillis ());
        calendar.set(Calendar.HOUR_OF_DAY,7);
        AlarmManager alarmManager=(AlarmManager) getSystemService (Context.ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent   alarm_Intent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating (AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis (),AlarmManager.INTERVAL_DAY,alarm_Intent);

       */

            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // SetRepeating() lets you specify a precise custom interval--in this case, 2 minutes.

            long time = new GregorianCalendar().getTimeInMillis() + 2 * 60 * 1000;

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, time,
                    1000 * 60 * 2, mPendingIntent);

            callToast("Alarm Scheduled for 2 min");


        }

    /**
     * Cancel the alarm.
     */
    private void stopAlarm(){
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.cancel(mPendingIntent);
        }

    // This method to display a toast message.
    private void callToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);

        toast.show();
    }


    @Override
    protected void onResume() {
        Log.i(TAG," On Resume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        saveNotificationSettings();
        Log.i(TAG,"On destroy");
        super.onDestroy();
    }


    @Override
    public int getActivityLayout() {
        return R.layout.activity_notifications;
    }
}


