package ch.openclassrooms.enyo1.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ch.openclassrooms.enyo1.mynews.controller.fragments.BusinessFragment;
import ch.openclassrooms.enyo1.mynews.controller.fragments.MostPopFragment;
import ch.openclassrooms.enyo1.mynews.controller.fragments.TopStoriesFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList=new ArrayList<>();
    private final List<String>mFragmentTitleList=new ArrayList<>();

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
       return mFragmentList.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 0: // Fragment # 0 - This will show MostPopFragment
                 return new TopStoriesFragment().newInstance();
            case 1: // Fragment # 1 - This will show FirstFragment different title
                return new MostPopFragment().newInstance();
            case 2: // Fragment # 2 - This will show SecondFragment
                return (new BusinessFragment ()).newInstance();
            default:
                return null;
        }*/

        return mFragmentList.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {

       /* switch (position){
            case 0:
                return "TOP STORIES";
            case 1:
                return "MOST POPULAR";
            case 2:
                return "BUSINESS";
            default:
                return null;
        }*/

       return mFragmentTitleList.get(position);

    }

    /**
     * This method to add fragment to the fragment list.
     * @param fragment,
     *        the fragment to add.
     * @param title,
     *         the title of the fragment.
     */
    public void addFragment(Fragment fragment,String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }

}


