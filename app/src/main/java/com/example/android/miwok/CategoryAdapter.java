package com.example.android.miwok;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

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

    @Override
    public int getCount() {
        return 4;
    }
}
