package com.example.money_split_f1.ui.startScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivityStartBinding;
import com.example.money_split_f1.Event.EventCreationActivity;
import com.example.money_split_f1.profile.ProfileActivity;
import com.google.android.material.navigation.NavigationBarView;


public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    public SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //------- setup a logo -------
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_logo, null);
        actionBar.setCustomView(view);
        //------- setup a logo -------

        replaceFragment(new EventFragment()); //home view is event fragment

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.event_button:
                        replaceFragment(new EventFragment());
                        break;

                    case R.id.analysis:
                        replaceFragment(new AnalysisFragment());
                        break;

                    case R.id.notification:
                        replaceFragment(new NotificationFragment());
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        this.searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        this.searchView.setQueryHint(SuperApplication.getContext().getResources().getString(R.string.search_hint));

        return super.onCreateOptionsMenu(menu);
    }




    /**This methode is helper function that helps to switch between fragments
     * @param fragment
     * */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    /**This methode is to make icons in toolbar clickable
     * @param item
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent1 = new Intent(this, ProfileActivity.class);
            this.startActivity(intent1);
            return true;
        }

        if (id == R.id.addEvent){
            Intent intent = new Intent(this, EventCreationActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}