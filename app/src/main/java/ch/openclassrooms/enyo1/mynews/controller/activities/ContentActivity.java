package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.controller.fragments.BaseFragment;

/**
 * This activity to display the content of
 */
public class ContentActivity extends AppCompatActivity {

    // For Debug
    private static final String TAG = ContentActivity.class.getSimpleName();

    @BindView(R.id.activity_web_view_layout) WebView mWebView;
    @BindView(R.id.toolbar) Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        // Get & serialise all views
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra(BaseFragment.BUNDLE_CONTENT_URL);
        configureToolbar();

        mWebView.setWebViewClient(new WebViewClient());

        // Show the page of the news
        mWebView.loadUrl(url);

    }

    private void configureToolbar(){
        //Set the toolbar
        setSupportActionBar(mToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
