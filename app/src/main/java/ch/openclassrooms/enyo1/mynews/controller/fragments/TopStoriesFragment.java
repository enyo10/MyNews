package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.topStories.Result;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import ch.openclassrooms.enyo1.mynews.view.TopStoriesAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends BaseFragment {

    private Disposable disposable;
    private List<Result> mResults;
    private TopStoriesAdapter mTopStoriesAdapter;


    RecyclerView mRecyclerView;



    @Override
    public BaseFragment newInstance() {
        TopStoriesFragment topStoriesFragment=new TopStoriesFragment ();
        topStoriesFragment.title="TOP STORIES";

        return topStoriesFragment;
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_top_stories;
    }

    @Override
    protected void configureDesign(View v) {
        mRecyclerView =v.findViewById(R.id.fragment_top_stories_recycler_view);
        configureRecyclerView();

        executeHttpRequestWithRetrofit();

    }

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // 3.1 - Reset list
        this.mResults = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.mTopStoriesAdapter = new TopStoriesAdapter(this.mResults, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.mRecyclerView.setAdapter(this.mTopStoriesAdapter);
        // 3.4 - Set layout manager to position the items
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        // this.updateUIWhenStartingHTTPRequest();

        this.disposable = NYTimesStream.streamFetchTopStories("UqsVUuAGooyAyaJPZrwM45HG454PT72r","home")
                .subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories topStories) {
                // 6 - Update RecyclerView after getting results from Github API
                Log.i("TAG","Download");

               updateUI(topStories.getResults());

               Log.i("TAG","Number of Results "+  topStories.getNumResults());

            }

            @Override
            public void onError(Throwable e) {

                Log.e("TAG","Youps aie aie "+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {

                Log.i("TAG","Downloaded");
            }
        });
    }


    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<Result> results){
        mResults.addAll(results);
        mTopStoriesAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<NYTimesArticle> convertToArticlesList(Object data) {
        TopStories topStories = (TopStories) data;
        ArrayList<NYTimesArticle>list =new ArrayList<>();

        for(Result result:topStories.getResults()){
            NYTimesArticle article=new NYTimesArticle();
            article.setDate(result.getCreatedDate());
            article.setSection(result.getSection());
            article.setTitle(result.getTitle());
            article.setNewsURL(result.getUrl());
             if(result.getMultimedia().size()!=0)
                 article.setImageURL(result.getMultimedia().get(0).getUrl());

             list.add(article);
        }



        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



}
