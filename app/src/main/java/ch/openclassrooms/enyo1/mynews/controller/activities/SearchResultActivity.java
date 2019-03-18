package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import ch.openclassrooms.enyo1.mynews.utils.DateFormatter;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import ch.openclassrooms.enyo1.mynews.view.NYTimesArticleAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.activity_search_result_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.activity_search_result_recycler_view) RecyclerView mRecyclerView;


    private Map<String,String> mFilterMap;

    private Disposable mDisposable;
    private  List<NYTimesArticle> mNYTimesArticles;
    private NYTimesArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        configureToolbar();

        Intent intent = getIntent();
        String jsonString =intent.getStringExtra(SearchActivity.BUNDLE_SEARCH_FILTER);
        try {
            mFilterMap =jsonToMap(jsonString);
            Log.i("TAG","JsonMap: "+mFilterMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        configureRecyclerView();
        executeHttpRequestWithRetrofit();

    }


    private Map<String, String>jsonToMap(String jsonString)throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(jsonString);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }

    private void configureToolbar(){

        //Set the toolbar
        setSupportActionBar(mToolbar);

        // Get a support ActionBar corresponding to this toolbar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search result");
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    /**
     * This method to configure the recycler view.
     */
    protected void configureRecyclerView(){
        // Reset the list.
        this.mNYTimesArticles= new ArrayList<>();
        // Create adapter and pass the list of article and passing reference to callback.
        this.mAdapter = new NYTimesArticleAdapter(this.mNYTimesArticles, Glide.with(this));
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // Retrofit request.
    protected void executeHttpRequestWithRetrofit() {
        String key="UqsVUuAGooyAyaJPZrwM45HG454PT72r";

        this.mDisposable= NYTimesStream.streamFetchArticlesSearch(key,mFilterMap)
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch nytArticles) {

                        Log.i("TAG","Article search Downloading...");
                        Log.i("TAG","Article search result size : " +nytArticles.getResponse().getDocs().size());
                        ArrayList<NYTimesArticle>articles=convertToArticlesList(nytArticles);

                        updateUI(articles);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG","aie, error in Search Result activity: "  +Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // -------------------
    // UPDATE UI
    // -------------------

    protected void updateUI(ArrayList<NYTimesArticle> articles){
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mNYTimesArticles.clear();
        this.mNYTimesArticles.addAll(articles);
        this.mAdapter.notifyDataSetChanged();
    }

    // Convert search article object to NYTImeArticle object.
    protected ArrayList<NYTimesArticle> convertToArticlesList(Object data) {

        ArticleSearch articleSearch = (ArticleSearch) data;
        ArrayList<NYTimesArticle> list = new ArrayList<>();
        //Here we recover only the elements of the query that interests us
        for (Doc docs : articleSearch.getResponse().getDocs()) {

            //  --> Create a news <--
            NYTimesArticle nyTimesArticle = new NYTimesArticle();

            // -- Affected newsURL

            // -- Affected newsURL
            nyTimesArticle.setURL(docs.getWebUrl());

            // -- Affected imageURL
            // Test if an image is present
            if (docs.getMultimedia().size() != 0) {
                nyTimesArticle.setImageURL("https://www.nytimes.com/" + docs.getMultimedia().get(0).getUrl());
            }

            // -- Affected section label ( section > subSection )
            String section = docs.getNewsDesk();
            if (docs.getSectionName() != null) section = section + " > " + docs.getSectionName();
            nyTimesArticle.setSection(section);

            // -- Affected date label ( SSAAMMJJ )
            nyTimesArticle.setDate(DateFormatter.dateFormatYYYYMMJJ(docs.getPubDate()));

            // -- Affected Title
            nyTimesArticle.setTitle(docs.getSnippet());


            // Add news at Lit
            list.add(nyTimesArticle);
        }

    return list;

        // Sort the newsList by createdDate in Descending
        //Collections.sort(listNYTimesNews, new NYTimesNews());
       // Collections.reverse(listNYTimesNews);
    }

    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposeWhenDestroy();
    }
}
