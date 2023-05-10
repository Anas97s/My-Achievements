package com.example.money_split_f1.Guest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivityGuestBinding;
import com.google.android.material.navigation.NavigationBarView;

public class GuestActivity extends AppCompatActivity {
    private ActivityGuestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //adding logo to top left of toolbar
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_logo, null);
        actionBar.setCustomView(view);


        replaceFragment(new EventGuestFragment()); //main view is Veranstaltungen

        /**Click on logo to switch back to main screen App*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuestActivity.this, MainActivity.class));
            }
        });


        binding.bottomNavigationViewGuest.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.event_button:
                        replaceFragment(new EventGuestFragment());
                        break;

                    case R.id.notification:
                        replaceFragment(new SigninOrSignUpFragment());
                        break;
                    case R.id.analysis:
                        replaceFragment(new SigninOrSignUpFragment());
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
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(SuperApplication.getContext().getResources().getString(R.string.search_hint));
        return super.onCreateOptionsMenu(menu);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutGuest, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent1 = new Intent(this, ProfileGuestActivity.class);
            this.startActivity(intent1);
            return true;
        }

        if (id == R.id.addEvent){
            startActivity(new Intent(GuestActivity.this, EventCreationGuestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}