package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.openclassrooms.enyo1.mynews.R;

/**
 * This activity to display the content of
 */
public class ArticleContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);
    }
}
