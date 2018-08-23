package com.example.kuaibang.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kuaibang.R;
import com.example.kuaibang.adapter.HelpViewPagerFgAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageHelpFragment extends Fragment {

    private TabLayout helpTablayout;
    private ViewPager helpViewPager;

    public static final String[] tabTitle = new String[]{"待帮助","正在帮助","已帮助"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_help_fg,container,false);

        helpTablayout = view.findViewById(R.id.help_tabLayout);
        helpViewPager = view.findViewById(R.id.help_viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HelpsFragment.newInstance());
        fragments.add(HelpingFragment.newInstance());
        fragments.add(HelpedFragment.newInstance());
        HelpViewPagerFgAdapter viewPagerFgAdapter = new HelpViewPagerFgAdapter(getFragmentManager(),fragments);
        helpViewPager.setAdapter(viewPagerFgAdapter);

        helpTablayout.setupWithViewPager(helpViewPager);

        for(int i=0;i<helpTablayout.getTabCount();i++){
            helpTablayout.getTabAt(i).setText(tabTitle[i]);
        }


        helpTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getContext(),"当前选择为:"+tab.getText(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        helpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }
}
