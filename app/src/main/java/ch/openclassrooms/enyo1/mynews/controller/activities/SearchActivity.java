package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.DateFormatter;
import ch.openclassrooms.enyo1.mynews.utils.Filters;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG=SearchActivity.class.getSimpleName();


    @BindView(R.id.toolbar) Toolbar mToolbar;

    @BindView(R.id.activity_search_edit_text) EditText mEditText;
    @BindView(R.id.activity_search_begin_date)EditText mBeginDate;
    @BindView(R.id.activity_search_end_date)EditText mEndDate;

    // The check boxes.
    @BindView(R.id.checkBox_arts) CheckBox mCheckBox_arts;
    @BindView(R.id.checkBox_business)CheckBox mCheckBox_business;
    @BindView(R.id.checkBox_politics)CheckBox mCheckBox_politics;
    @BindView(R.id.checkBox_sports)CheckBox mCheckBox_sports;
    @BindView(R.id.checkBox_travel)CheckBox mCheckBox_travel;
    @BindView(R.id.checkBox_entrepreneurs)CheckBox mCheckBox_entrepreneurs;


    // Declarations for management of the dates Fields with a DatePickerDialog
    private DatePickerDialog mBeginDatePickerDialog;
    private DatePickerDialog mEndDatePickerDialog;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;

    private Filters mFilters;

    public static final String BUNDLE_SEARCH_FILTER = "BUNDLE_SEARCH_FILTER";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mFilters=new Filters();

        ButterKnife.bind(this);
        manageDateFields();
        configureToolBar();
    }

    /**
     * This method to configure the toolbar.
     */
    public void configureToolBar(){
        mToolbar.setTitle("Search Articles");
        setSupportActionBar(mToolbar);

         ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
         actionBar.setDisplayHomeAsUpEnabled(true);

    }

    // Click on BeginDate Field
    @OnClick(R.id.activity_search_begin_date)
    public void onClickBeginDate(View v) {
        mBeginDatePickerDialog.show();
    }
    // Click on EndDate Field
    @OnClick(R.id.activity_search_end_date)
    public void onClickEndDate(View v) {
        mEndDatePickerDialog.show();
    }
    /**
     * This method to save or remove the selected category.
     * @param v,
     *      the view clicked.
     */
    public void onCheckBoxClicked(View v){
        // Is the view now checked?
        boolean checked = ((CheckBox) v).isChecked();

        // Check which checkbox was clicked
        switch(v.getId()) {
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


    // -------------------
    // MANAGE DATE FIELDS
    // -------------------
    private void manageDateFields() {
        newCalendar = Calendar.getInstance();
        displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setBeginDateField();
        setEndDateField();
    }

    // Manage BeginDate Field

    public void setBeginDateField() {

        // Create a DatePickerDialog and manage it
        mBeginDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                // Display date selected
                mBeginDate.setText(displayDateFormatter.format(newDate.getTime()));
                Log.i("TAG","in set Begin date : "+mBeginDate.getText());

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    // Manage EndDate Field

    private void setEndDateField() {
        // Create a DatePickerDialog and manage it
        mEndDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                // Display date selected
                mEndDate.setText(displayDateFormatter.format(newDate.getTime()));
                Log.i("TAG","in set Begin date : "+mEndDate.getText());

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    // This method to display a toast message.
    private void callToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);

        toast.show();
    }

    /**
     * This method to send the request to the api.
     * @param view,
     *      the view that fire the request.
     */
    @OnClick(R.id.activity_search_button)
    public void sendRequest(View view){
        mFilters.setBeginDate(DateFormatter.dateFormatYYYYMMJJ(mBeginDate.getText().toString()));
        mFilters.setEndDate(DateFormatter.dateFormatYYYYMMJJ(mEndDate.getText().toString()));

        Log.i(TAG,DateFormatter.dateFormatYYYYMMJJ(mEndDate.getText().toString()));

        boolean searchWordsISSet=false;
        boolean categoryIsSet=false;
        //
        if(mEditText.getText().toString().equals(""))
            callToast("Search text must be set.");
        else{
            mFilters.setKeyWord(mEditText.getText().toString());
            searchWordsISSet=true;
        }

        if(mFilters.getSelectedValues().size()==0)
            callToast("At least one category must be chosen");
        else
            categoryIsSet=true;

        if(searchWordsISSet  &categoryIsSet){
            Log.i(TAG,"OK");
           // JSONObject jsonObject =new JSONObject(mFilters.getFilters());
           String stringMap= Filters.mapToJsonString(mFilters.getFilters());
            Log.i(TAG," JSon : " +stringMap);

            Intent resultSearch = new Intent(SearchActivity.this,SearchResultActivity.class);
           resultSearch.putExtra(BUNDLE_SEARCH_FILTER,stringMap);
           startActivity(resultSearch);

        }

    }
}
