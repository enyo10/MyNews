package ch.openclassrooms.enyo1.mynews.controller.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import icepick.Icepick;
import icepick.State;

public abstract class BaseFragment extends Fragment {
    @State
    String title;

   public BaseFragment(){}



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get layout identifier from abstract method
        View view = inflater.inflate(getFragmentLayout(), container, false);
        configureDesign (view);
        Icepick.restoreInstanceState (this,savedInstanceState);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }




    /**
     * This method to create a fragment with the given title.
     *       title of the fragment.
     * @return BaseFragment,
     *        the fragment to be create.
     */
    public  abstract BaseFragment newInstance();

    /**
     * This method to get the fragment layout resource id.
     * @return, id,
     *         the resource id.
     */
    protected abstract int getFragmentLayout();

    /**
     * This method to configure the fragment view.
     */
    protected abstract void configureDesign(View v);




}
