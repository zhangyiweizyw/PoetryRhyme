package com.example.poetryrhymedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.poetryrhymedemo.Fragment.CommunityFragment;
import com.example.poetryrhymedemo.Fragment.GameFragment;
import com.example.poetryrhymedemo.Fragment.HomeFragment;
import com.example.poetryrhymedemo.Fragment.WriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.tab_home:{
                            selectedFragment=new HomeFragment();
                            break;
                        }
                        case R.id.tab_community:{
                            selectedFragment=new CommunityFragment();
                            break;
                        }
                        case R.id.tab_write:{
                            selectedFragment=new WriteFragment();
                            break;
                        }
                        case R.id.tab_game:{
                            selectedFragment=new GameFragment();
                            break;
                        }
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}
