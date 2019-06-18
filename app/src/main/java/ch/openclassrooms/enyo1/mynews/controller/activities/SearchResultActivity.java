package ch.openclassrooms.enyo1.mynews.controller.activities;

import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import ch.openclassrooms.enyo1.mynews.utils.Filters;
import ch.openclassrooms.enyo1.mynews.utils.ItemClickSupport;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import ch.openclassrooms.enyo1.mynews.view.NYTimesArticleAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static ch.openclassrooms.enyo1.mynews.controller.fragments.BaseFragment.BUNDLE_CONTENT_URL;


public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG=SearchActivity.class.getSimpleName();

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
        // Get the json string of the filter map and convert it to map object.

        Intent intent = getIntent();
        String jsonString =intent.getStringExtra(SearchActivity.BUNDLE_SEARCH_FILTER);
        try {
            mFilterMap = Filters.jsonToMap(jsonString);
            Log.i(TAG,"JsonMap: "+mFilterMap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        configureSwipeRefreshLayout();
        configureRecyclerView();
        configureOnClickRecyclerView();
        executeHttpRequestWithRetrofit();
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
    /**
     * This method to refresh the layout.
     */
    protected void configureSwipeRefreshLayout(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }


    // Retrofit request.
    protected void executeHttpRequestWithRetrofit() {
        String key=getString(R.string.api_key);

        this.mDisposable= NYTimesStream.streamFetchArticlesSearch(key,mFilterMap)
                .subscribeWith(new DisposableObserver<ArticleSearch>() {
                    @Override
                    public void onNext(ArticleSearch nytArticles) {
                        Log.i(TAG, "filter to execute "+ mFilterMap.toString());

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

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_article_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);

                        NYTimesArticle article = mAdapter.getItem(position);
                        callArticleContentActivity(article.getURL());

                        Log.i("TAG"," article selected : "+article.getURL());
                    }
                });
    }

    // Launch the ContentActivity
    // Param : 1 _ Url to display
    protected void callArticleContentActivity(String url){
        Intent myIntent = new Intent(this, ContentActivity.class);
        myIntent.putExtra(BUNDLE_CONTENT_URL,url);
        this.startActivity(myIntent);
    }

    // This method to display a toast message.
    private void callToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT);

        toast.show();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    protected void updateUI(ArrayList<NYTimesArticle> articles){
        int articleSize=articles.size();
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mNYTimesArticles.clear();
        if(articleSize==0)
            callToast("No result fund.");

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
            nyTimesArticle.setURL(docs.getWebUrl());

            // -- Affected imageURL
            // Test if an image is present
            if (docs.getMultimedia().size() != 0) {
                nyTimesArticle.setImageURL("https://www.nytimes.com/" + docs.getMultimedia().get(0).getUrl());
            }

            // -- Affected section label ( section > subSection )
             String section= docs.getSectionName();

            if (!docs.getNewsDesk().equals("")) section = section + " > " + docs.getNewsDesk();
            nyTimesArticle.setSection(section);
            
            nyTimesArticle.setDate(docs.getPubDate());

            // -- Affected Title
            nyTimesArticle.setTitle(docs.getSnippet());

            // Add news at Lit
            list.add(nyTimesArticle);
        }

    return list;

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
