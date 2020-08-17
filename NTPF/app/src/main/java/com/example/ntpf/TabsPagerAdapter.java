package com.example.ntpf;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ntpf.Tabs.CercaFragment;
import com.example.ntpf.Tabs.ChatFragment;
import com.example.ntpf.Tabs.FavoritosFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    int[] TITULOS_TABS = new int[] {R.string.tab_text_uno};
    Context mContext;

    public TabsPagerAdapter(Context mContext, @NonNull FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0: return new CercaFragment();
            //case 1: return new ChatFragment();
            //case 2: return new FavoritosFragment();
            default: return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TITULOS_TABS[position]);
    }

    @Override
    public int getCount(){return 1;}


}
