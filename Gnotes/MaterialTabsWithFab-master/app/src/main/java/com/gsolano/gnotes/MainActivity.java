package com.gsolano.gnotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.gsolano.gnotes.tabs.FragmentFavorites;
import com.gsolano.gnotes.tabs.FragmentHome;
import com.gsolano.gnotes.tabs.FragmentRespaldo;
import com.gsolano.gnotes.tabs.FragmentProfile;
import com.gsolano.gnotes.tabs.adapter.ViewPagerAdapter;
//import com.gsolano.gnotes.design.CustomPlayLine;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private boolean isPlay = false;
    //private CustomPlayLine customPlayLine;
    public Realm realm;
    public String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("Correo")!= null)
        {
            User=bundle.getString("Correo");
        }

        realm = Realm.getDefaultInstance();
        setType();
        setToolbar();
        setToolbarCartIcon();
        setToolbarSearchIcon();
        setTabLayout();

        //setFab();
    }

    private void setType() {
        //customPlayLine = new CustomPlayLine(findViewById(R.id.customPlayLine));
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

        adapter.addFragment(new FragmentHome(User), "");
        adapter.addFragment(new FragmentProfile(), getString(R.string.tab_item_1));
        adapter.addFragment(new FragmentFavorites(), getString(R.string.tab_item_2));
        adapter.addFragment(new FragmentRespaldo(), getString(R.string.tab_item_0));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
    }
    public String isnull(String campo, String resp){
        if(campo ==null || campo.isEmpty() || campo.equals("")){
            return resp;
        }
        return campo;
    }


    private void playLine() {

        //newNoteFragment frag2 = new newNoteFragment();
        //getSupportFragmentManager().beginTransaction().replace(new FragmentHome(), frag2);



        /*if (isPlay) {
            customPlayLine.setMusicName("Kiss - I Love It Loud (Official Music Video)");
            customPlayLine.show();
        } else {
            customPlayLine.hide();
        }*/
    }

    private void changeFabIcon(FloatingActionButton floatingActionButton) {

            floatingActionButton.setImageResource(R.drawable.plus);

    }




}
