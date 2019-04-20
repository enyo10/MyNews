package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
import icepick.Icepick;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG=BaseActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.activity_notification_words_edit)
    EditText mEditText;

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

    protected HashMap<Integer, View> mViewMap =new HashMap<>();
    protected Filters mFilters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        ButterKnife.bind(this);

        keepTrackOfCheckBoxes();
        mFilters=new Filters();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this,outState);

    }

    /**
     * This method to configure the toolbar.
     */
    public void configureToolBar(){

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    /**
     * With this method, we put all check boxes in a map to keep tack of them.
     */
    protected void keepTrackOfCheckBoxes(){

        mViewMap.put(mCheckBox_arts.getId(),mCheckBox_arts);
        mViewMap.put(mCheckBox_business.getId(),mCheckBox_business);
        mViewMap.put(mCheckBox_entrepreneurs.getId(),mCheckBox_entrepreneurs);
        mViewMap.put(mCheckBox_politics.getId(),mCheckBox_politics);
        mViewMap.put(mCheckBox_travel.getId(),mCheckBox_travel);
        mViewMap.put(mCheckBox_sports.getId(),mCheckBox_sports);

    }
    /**
     * Add selected category to the filter map.
     * @param view,
     *       the view selected.
     */
    public void onCheckBoxClicked(View view) {

        for (View v : mViewMap.values()) {
            if ( ((CheckBox) v).isChecked())
                mFilters.addSelectedCategory(v.getTag().toString());
            else
                mFilters.removeSelectedCategory(v.getTag().toString());
        }
        Log.i(TAG, "selected Value " +mFilters.getSelectedValues().size());

    }


    public abstract int getActivityLayout();


}
