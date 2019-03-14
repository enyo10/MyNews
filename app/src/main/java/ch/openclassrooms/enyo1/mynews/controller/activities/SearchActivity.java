package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
import io.reactivex.disposables.Disposable;

public class SearchActivity extends AppCompatActivity {

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

    // Declare Subscription
    protected Disposable mDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mFilters=new Filters();

        ButterKnife.bind(this);
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
                    mFilters.addValue("Arts");
            else
                // Remove the remove the value
                mFilters.removeValue("Arts");
                break;
            case R.id.checkBox_business:
                if (checked)
                 mFilters.addValue("Business");
            else
                mFilters.removeValue("Business");
                break;
            case R.id.checkBox_entrepreneurs:
                if (checked)
                    // Put value on filters
                    mFilters.addValue("Entrepreneurs");
                else
                    // Remove the remove the value
                    mFilters.removeValue("Entrepreneurs");
                break;
            case R.id.checkBox_politics:
                if (checked)
                    mFilters.addValue("Politics");
                else
                    mFilters.removeValue("Politics");
                break;

            case R.id.checkBox_sports:
                if (checked)
                    // Put value on filters
                    mFilters.addValue("Sport");
                else
                    // Remove the remove the value
                    mFilters.removeValue("Sport");
                break;
            case R.id.checkBox_travel:
                if (checked)
                    mFilters.addValue("Travel");
                else
                    mFilters.removeValue("Travel");
                break;

        }
        Log.i("TAG"," values clicked : " +mFilters.toString());

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
    private void setBeginDateField() {
        // Create a DatePickerDialog and manage it
        mBeginDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                // Display date selected
                mBeginDate.setText(displayDateFormatter.format(newDate.getTime()));

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

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
