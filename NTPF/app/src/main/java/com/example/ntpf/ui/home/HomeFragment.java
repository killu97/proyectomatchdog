package com.example.ntpf.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.ntpf.R;
import com.example.ntpf.TabsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    TabsPagerAdapter tabs;
    TabLayout tab_layout;
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        tab_layout = root.findViewById(R.id.tab_layout);

        tabs = new TabsPagerAdapter(getContext(), getChildFragmentManager());

        viewPager.setAdapter(tabs);

        tab_layout.setupWithViewPager(viewPager);

        return root;
    }
}