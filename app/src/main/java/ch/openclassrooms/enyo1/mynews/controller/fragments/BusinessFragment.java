package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.ArticleSearch;
import ch.openclassrooms.enyo1.mynews.models.articleSearch.Doc;
import ch.openclassrooms.enyo1.mynews.models.topStories.Result;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.DateFormatter;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends BaseFragment {
    private static final String TAG = BusinessFragment.class.getSimpleName();


    @Override
    public BaseFragment newInstance() {
        BusinessFragment businessFragment=new BusinessFragment ();
        businessFragment.title="BUSINESS";

        return businessFragment;

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_base_layout;

    }

    @Override
    protected void configureDesign(View v) {
        mSwipeRefreshLayout =v.findViewById(R.id.fragment_swipe_container);
        mRecyclerView =v.findViewById(R.id.fragment_recycler_view);

    }


    @Override
    protected ArrayList<NYTimesArticle> convertToArticlesList(Object data) {

        TopStories businessArticles =(TopStories) data;
        ArrayList<NYTimesArticle>articles =new ArrayList<>();

        if(businessArticles.getResults().size()!=0) {

            for (Result result : businessArticles.getResults()) {
                NYTimesArticle article = new NYTimesArticle();
                article.setDate(result.getCreatedDate());

                if(result.getSubsection()!=null)
                    article.setSection(result.getSection()+" > "+result.getSubsection());
                else
                    article.setSection(result.getSection());

                article.setTitle(result.getTitle());
                article.setURL(result.getUrl());

                if (result.getMultimedia().size() != 0)
                    article.setImageURL(result.getMultimedia().get(0).getUrl());

                articles.add(article);
            }
            Log.i(TAG, "Converting success");
        }
        return articles;
    }

    @Override
    protected void executeHttpRequestWithRetrofit() {
        String api_key= getResources().getString(R.string.api_key);

        this.mDisposable=NYTimesStream.streamFetchTopStories(api_key,"business")
                .subscribeWith(new DisposableObserver<TopStories>() {
                    @Override
                    public void onNext(TopStories business) {
                        Log.i(TAG,"Downloading Top story article");
                        updateUI(convertToArticlesList(business));

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"OOOps, aie aie "+Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"Downloaded");

                    }
                });
    }

}
