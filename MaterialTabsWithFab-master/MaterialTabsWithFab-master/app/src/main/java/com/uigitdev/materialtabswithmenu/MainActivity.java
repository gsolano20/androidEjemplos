package com.uigitdev.materialtabswithmenu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.uigitdev.materialtabswithmenu.tabs.FragmentFavorites;
import com.uigitdev.materialtabswithmenu.tabs.FragmentHome;
import com.uigitdev.materialtabswithmenu.tabs.FragmentLibrary;
import com.uigitdev.materialtabswithmenu.tabs.FragmentPlaylist;
import com.uigitdev.materialtabswithmenu.tabs.adapter.ViewPagerAdapter;
import com.uigitdev.materialtabswithmenu.uigitdev.design.CustomPlayLine;
import com.uigitdev.materialtabswithmenu.uigitdev.design.ToolbarIconBadge;

public class MainActivity extends AppCompatActivity {
    private boolean isPlay = false;
    private CustomPlayLine customPlayLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setType();
        setToolbar();
        setToolbarCartIcon();
        setToolbarSearchIcon();
        setTabLayout();
        setFab();
    }

    private void setType() {
        customPlayLine = new CustomPlayLine(findViewById(R.id.customPlayLine));
    }

    @SuppressLint("ResourceType")
    public void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.toolbar_title));
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)));
    }

    @SuppressLint("ResourceType")
    private void setToolbarCartIcon() {
    /*    final ToolbarIconBadge toolbarIconBadge = new ToolbarIconBadge(findViewById(R.id.toolbar_badge));
        toolbarIconBadge.setIconColor(getString(R.color.colorTitle));
        toolbarIconBadge.setIcon(R.drawable.ic_cart);

        toolbarIconBadge.toolbar_body_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarIconBadge.setCount(toolbarIconBadge.getCount() + 1);
            }
        });*/
    }

    @SuppressLint("ResourceType")
    private void setToolbarSearchIcon() {
      /*  final ToolbarIconBadge toolbarIconSearch = new ToolbarIconBadge(findViewById(R.id.toolbar_search));
        toolbarIconSearch.setIconColor(getString(R.color.colorTitle));
        toolbarIconSearch.setIcon(R.drawable.ic_search);
        toolbarIconSearch.hideBagde();

        toolbarIconSearch.toolbar_body_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Search Icon", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private void setTabLayout() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentHome(), "");
        adapter.addFragment(new FragmentLibrary(), getString(R.string.tab_item_0));
        adapter.addFragment(new FragmentPlaylist(), getString(R.string.tab_item_1));
        adapter.addFragment(new FragmentFavorites(), getString(R.string.tab_item_2));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
    }

    private void setFab() {
        final FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = !isPlay;
                changeFabIcon(floatingActionButton);
                playLine();
            }
        });
    }

    private void playLine() {
        if (isPlay) {
            customPlayLine.setMusicName("Kiss - I Love It Loud (Official Music Video)");
            customPlayLine.show();
        } else {
            customPlayLine.hide();
        }
    }

    private void changeFabIcon(FloatingActionButton floatingActionButton) {
        if (isPlay) {
            floatingActionButton.setImageResource(R.drawable.ic_pause);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_play);
        }
    }
}
