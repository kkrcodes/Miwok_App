package com.example.android.miwok;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    /**
     * Return the fragment of each category based on the tab position
     * @param position the position of the tab in the viewpager
     * @return the fragment for the category
     */
    @Override
    public Fragment getItem(int position) {
       switch(position) {
           case 0: return new NumbersFragment();
           case 1: return new FamilyFragment();
           case 2: return new ColorsFragment();
           case 3: return new PhrasesFragment();
       }
        return null;
    }

    /**
     * Return the number of fragments in the viewpager
     * @return
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * Return the page title for the fragments
     * @param position the position of the tab in the viewpager
     * @return the title for the tab
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return mContext.getString(R.string.category_numbers);
            case 1: return mContext.getString(R.string.category_family);
            case 2: return mContext.getString(R.string.category_colors);
            case 3: return mContext.getString(R.string.category_phrases);
        }
        return null;
    }
}
