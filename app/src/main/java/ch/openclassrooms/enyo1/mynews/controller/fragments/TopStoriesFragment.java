package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.openclassrooms.enyo1.mynews.R;

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






}
