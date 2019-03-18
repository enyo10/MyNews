package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.topStories.Result;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.ItemClickSupport;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import ch.openclassrooms.enyo1.mynews.view.NYTimesArticleAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends BaseFragment {


    @Override
    public BaseFragment newInstance() {
        TopStoriesFragment topStoriesFragment=new TopStoriesFragment ();
        topStoriesFragment.title="TOP STORIES";

        return topStoriesFragment;
    }

    public TopStoriesFragment() {
        // Required empty public constructor
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

        TopStories topStories = (TopStories) data;
        ArrayList<NYTimesArticle>list =new ArrayList<>();

        if(topStories.getResults().size()!=0) {

            for (Result result : topStories.getResults()) {
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

                list.add(article);
            }

            Log.i("TAG", "Converting success");
        }

        return list;
    }



    @Override
    protected void executeHttpRequestWithRetrofit() {

        this.mDisposable = NYTimesStream.streamFetchTopStories("UqsVUuAGooyAyaJPZrwM45HG454PT72r","home")
                .subscribeWith(new DisposableObserver<TopStories>() {
                    @Override
                    public void onNext(TopStories topStories) {
                        // 6 - Update RecyclerView after getting results from Github API
                        Log.i("TAG"," Top stories Download...");
                        ArrayList<NYTimesArticle>articles=convertToArticlesList(topStories);
                        updateUI(articles);
                        Log.i("TAG","Number of top stories "+  topStories.getNumResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","OOOps, aie aie "+Log.getStackTraceString(e));
                    }
                    @Override
                    public void onComplete() {
                        Log.i("TAG","Downloaded");
                    }
                });

    }

    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState) {
        SwipeRefreshLayout swipeRefreshLayout =(SwipeRefreshLayout)inflater.inflate(getFragmentLayout(),parent,false);
        return swipeRefreshLayout;
    }


}
