package ch.openclassrooms.enyo1.mynews.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;
import ch.openclassrooms.enyo1.mynews.view.NYTimesArticleAdapter;
import icepick.Icepick;
import icepick.State;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {
    @State
    String title;

    Disposable mDisposable;
    List<NYTimesArticle>mNYTimesArticles;
    NYTimesArticleAdapter mAdapter;

    // FOR DESIGN
    // SwipeRefreshLayout and RecyclerView declaration.

    @BindView(R.id.fragment_swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_recycler_view) RecyclerView mRecyclerView;

    View mView;

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
     // View  view = inflater.inflate(R.layout.fragment_base_layout, container, false);

        configureDesign(view);
        configureSwipeRefreshLayout();
        configureRecyclerView();
        executeHttpRequestWithRetrofit();
        ButterKnife.bind(this,view);
        Icepick.restoreInstanceState (this,savedInstanceState);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
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


    /**
     * This method to provide a view to our fragment.
     * @param inflater
     * @param parent
     * @param saveInstanceState
     * @return
     */
    public abstract View provideFragmentView(LayoutInflater inflater,ViewGroup parent,Bundle saveInstanceState);


}
