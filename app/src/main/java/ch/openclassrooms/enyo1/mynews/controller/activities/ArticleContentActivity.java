package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.controller.fragments.BaseFragment;

/**
 * This activity to display the content of
 */
public class ArticleContentActivity extends AppCompatActivity {

    // For Debug
    private static final String TAG = ArticleContentActivity.class.getSimpleName();

    @BindView(R.id.activity_web_view_layout)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        // Get & serialise all views
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra(BaseFragment.BUNDLE_ARTICLE_URL);
        configureToolbar();

        mWebView.setWebViewClient(new WebViewClient());

        // Show the page of the news
        mWebView.loadUrl(url);

    }

    private void configureToolbar(){
        Log.d(TAG, "configureToolbar: ");
        //Set the toolbar
        setSupportActionBar(mToolbar);

        // Get a support ActionBar corresponding to this toolbar

        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
