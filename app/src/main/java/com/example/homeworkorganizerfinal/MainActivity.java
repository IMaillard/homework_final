package com.example.homeworkorganizerfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager2 pager;
    TabLayout tabLayout;
    SlideAdapter adapter;

    SharedPreferences preferences;
    List<String> subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSubjectsFromPrefs();

        pager = findViewById(R.id.subject_pager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new SlideAdapter( this);
        pager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(subjects.get(position));

            }
        }).attach();

    }

    private void getSubjectsFromPrefs() {
        subjects = new ArrayList<>();
        if (preferences.getBoolean(getString(R.string.precalc_ref_key), true)) {
            subjects.add(getString(R.string.precalc));
        }
        if (preferences.getBoolean(getString(R.string.apbio_pref_key), true)) {
            subjects.add(getString(R.string.apbio));
        }
        if (preferences.getBoolean(getString(R.string.mobileapp_pref_key), true)) {
            subjects.add(getString(R.string.mobileapp));
        }
        if (preferences.getBoolean(getString(R.string.science_pref_key), true)) {
            subjects.add(getString(R.string.science));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_settings) {
            Toast.makeText(this, "Settings Action Selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class SlideAdapter extends FragmentStateAdapter {

        public SlideAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(subjects.get(position).equals(getString(R.string.precalc))){
                return new PrecalcFragment();
            }else if(subjects.get(position).equals(getString(R.string.apbio))){
                return new ApbioFragment();
            }else if(subjects.get(position).equals(getString(R.string.mobileapp))){
                return new MobileappFragment();
            }else {
                return new ScienceFragment();
            }

        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }
    }
}