package com.example.dnail1;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TableLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.dnail1.navigations.NavigationIconClickListener;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;


public class BookMapFragment extends Fragment {

    TabLayout tabs;
    ViewPager viewPager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_book_map, container, false);

        addControls(root);
        addEvents();
        setUpToolbar(root);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void addEvents() {
        // Tabs trip
        MyTabAdapter adapter = new MyTabAdapter(getFragmentManager());
        adapter.addFragment(new MapFragment(), "Tìm thợ");
        adapter.addFragment(new OtherFeaturesFragment(), "Tính năng khác");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        ActionBar actionBar = activity.getSupportActionBar();

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.branded_menu);

    }

    private void addControls(View root) {
        viewPager = root.findViewById(R.id.pager);
        tabs = root.findViewById(R.id.tabs);
        drawerLayout = root.findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
