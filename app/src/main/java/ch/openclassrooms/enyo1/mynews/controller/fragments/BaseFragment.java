package ch.openclassrooms.enyo1.mynews.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.controller.activities.ArticleContentActivity;
import ch.openclassrooms.enyo1.mynews.utils.ItemClickSupport;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.view.NYTimesArticleAdapter;
import icepick.Icepick;
import icepick.State;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {
    public static final String TAG = BusinessFragment.class.getSimpleName();


    @State
    String title;

    Disposable mDisposable;
    List<NYTimesArticle>mNYTimesArticles;
    NYTimesArticleAdapter mAdapter;
    public static final String BUNDLE_ARTICLE_URL = "BUNDLE_ARTICLE_URL";

    // FOR DESIGN
    // SwipeRefreshLayout and RecyclerView declaration.
    @BindView(R.id.fragment_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_recycler_view) RecyclerView mRecyclerView;

    /**
     * A constructor without argument.
     */
    public BaseFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this,view);

        configureDesign(view);
        configureSwipeRefreshLayout();
        configureRecyclerView();
        configureOnClickRecyclerView();
        executeHttpRequestWithRetrofit();

        Icepick.restoreInstanceState (this,savedInstanceState);
        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
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


    /**
     * This method to configure the recycler view.
     */
    protected void configureRecyclerView(){
        // Reset the list.
        this.mNYTimesArticles= new ArrayList<>();
        // Create adapter and pass the list of article and passing reference to callback.
        this.mAdapter = new NYTimesArticleAdapter(this.mNYTimesArticles,Glide.with(this));
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    /**
     * This method show the selected article in a Web view.
     */
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


    /**
     * This method launch the ArticleContentActivity that is a web view and show the content
     * of an article.
     * @param url,
     *       the url of the article content.
     */
    protected void callArticleContentActivity(String url){
        Intent myIntent = new Intent(getActivity(), ArticleContentActivity.class);
        myIntent.putExtra(BUNDLE_ARTICLE_URL,url);
        this.startActivity(myIntent);
    }

    /**
     * This method to update the user interface view with the article list by notify the change to
     * the adapter.
     * @param articles,
     *       the articles list.
     *
     */
    protected void updateUI(ArrayList<NYTimesArticle> articles){
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mNYTimesArticles.clear();
        this.mNYTimesArticles.addAll(articles);
        this.mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }


    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) this.mDisposable.dispose();
    }


    public abstract BaseFragment newInstance();

    /**
     * This method to get the fragment layout resource id.
     * @return id,
     *         the resource id.
     */
    protected abstract int getFragmentLayout();

    /**
     * This method to configure the fragment view.
     */
    protected abstract void configureDesign(View v);

    /**
     * This method to convert a data to an article list. This data is given as a object.
     * @param data,
     *            the data to convert to the list.
     * @return list,
     *            a list of NYTimesArticles.
     *
     */
    protected abstract ArrayList<NYTimesArticle> convertToArticlesList(Object data);

    /**
     * This method will execute the http request with retrofit.
     */
    protected abstract void executeHttpRequestWithRetrofit();


}
