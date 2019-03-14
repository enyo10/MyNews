package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.mostPopular.MostPopularArticle;
import ch.openclassrooms.enyo1.mynews.models.mostPopular.Result;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopFragment extends BaseFragment {


    @Override
    public BaseFragment newInstance() {
        MostPopFragment popFragment=new MostPopFragment ();
        popFragment.title="MOST POPULAR";

        return popFragment;
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
    protected ArrayList<NYTimesArticle> convertToArticlesList(Object data)
    {
        MostPopularArticle mostPopularArticle = (MostPopularArticle) data;
        ArrayList<NYTimesArticle>list =new ArrayList<>();

        if(mostPopularArticle.getResults().size()!=0) {

            for (Result result : mostPopularArticle.getResults()) {
                NYTimesArticle article = new NYTimesArticle();
                article.setDate(result.getPublishedDate());

                // -- Affected newsURL
                article.setURL(result.getUrl());

                // -- Affected imageURL
                // Test if an image is present
                if (result.getMedia().size() != 0) {
                    article.setImageURL(result.getMedia().get(0).getMediaMetadata().get(0).getUrl());
                }

                // -- Affected section label ( section > subSection )
                article.setSection(result.getSection());

               /* if(result.getSubsection()!=null)
                    article.setSection(result.getSection()+" > "+result.getSubsection());
                else
                    article.setSection(result.getSection());*/

                article.setTitle(result.getTitle());

                /*if (result.getMedia().size() != 0 && result.getMedia().get(0).getMediaMetadata().size()!=0)
                    article.setImageURL(result.getMedia().get(0).getMediaMetadata().get(0).getUrl());*/

                list.add(article);
            }

            Log.i("TAG", "Converting success");
        }

        return list;
    }


    @Override
    protected void executeHttpRequestWithRetrofit() {
        this.mDisposable =NYTimesStream.streamFetchMostPopArticles("UqsVUuAGooyAyaJPZrwM45HG454PT72r")
                .subscribeWith(new DisposableObserver<MostPopularArticle>() {
                    @Override
                    public void onNext(MostPopularArticle mostPopularArticle) {
                        Log.i("TAG","Downloading Most pop article");

                        ArrayList<NYTimesArticle>articles=convertToArticlesList(mostPopularArticle);
                        updateUI(articles);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG"," Most pop article : Error - > "+Log.getStackTraceString(e));

                    }

                    @Override
                    public void onComplete() {
                        Log.i("TAG","Most Pop  article downloaded");

                    }
                });



    }


    @Override
    public View provideFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState) {
        return null;
    }
}
