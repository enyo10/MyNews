package ch.openclassrooms.enyo1.mynews.controller.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.openclassrooms.enyo1.mynews.R;
import ch.openclassrooms.enyo1.mynews.utils.NYTimesArticle;

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

/* public BaseFragment newInstance(String title) {
        MostPopFragment popFragment=new MostPopFragment ();
        popFragment.title="MOST POPULAR";

        return popFragment;
    }*/

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_most_pop;
    }

    @Override
    protected void configureDesign(View v) {


    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_most_pop, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.most_popTv);
        String value= "MOST POPULAR";
        tvLabel.setText(value);

        return view;
    }*/

    @Override
    protected ArrayList<NYTimesArticle> convertToArticlesList(Object data) {
        return null;
    }
}
