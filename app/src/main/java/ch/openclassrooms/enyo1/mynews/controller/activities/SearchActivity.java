package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        configureToolBar();
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
}
