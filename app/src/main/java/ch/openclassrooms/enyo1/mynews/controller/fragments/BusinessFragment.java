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
public class BusinessFragment extends BaseFragment {



    @Override
    public BaseFragment newInstance() {
        BusinessFragment businessFragment=new BusinessFragment ();
        businessFragment.title="BUSINESS";

        return businessFragment;

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_business;
    }

    @Override
    protected void configureDesign(View v) {
        TextView tvLabel =  v.findViewById(R.id.businessTv);
        String value="BUSINESS";
        tvLabel.setText(value);

    }

    /* public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance(int page, String title){
        BusinessFragment businessFragment=new BusinessFragment ();
        Bundle args =new Bundle ();
        args.putInt ("someInt",page);
        args.putString ("someTitle",title);
        businessFragment.setArguments (args);
        return businessFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("someTitle");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_business, container, false);
        TextView tvLabel =  view.findViewById(R.id.businessTv);
        String value="BUSINESS";
        tvLabel.setText(value);
        return view;
    }
*/


}
