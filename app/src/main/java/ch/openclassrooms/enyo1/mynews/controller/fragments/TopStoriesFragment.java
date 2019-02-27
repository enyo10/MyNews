package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.models.topStories.TopStories;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesStream;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends BaseFragment {

    private Disposable disposable;


    @Override
    public BaseFragment newInstance() {
        TopStoriesFragment topStoriesFragment=new TopStoriesFragment ();
        topStoriesFragment.title="TOP STORIES";
        executeHttpRequestWithRetrofit();
        return topStoriesFragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_top_stories;
    }

    @Override
    protected void configureDesign(View v) {

    }

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        // this.updateUIWhenStartingHTTPRequest();

        this.disposable = NYTimesStream.streamFetchTopStories("UqsVUuAGooyAyaJPZrwM45HG454PT72r","sports")
                .subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories topStories) {
                // 6 - Update RecyclerView after getting results from Github API
                Log.i("TAG","Download");
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


    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



}
