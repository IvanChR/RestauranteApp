package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
public ViewPagerAdapter(@NonNull FragmentManager fm) {
    super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

}
    @NonNull
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return new TabComida();
            case 1:
                return new TabBebida();
            case 2:
                return new TabComplementos();
            default:
                return new TabComida();
        }
    }

    @Override
    public int getCount(){
        return 3;
    }
}

