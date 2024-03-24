package com.example.duolingoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.AppBarConfiguration;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duolingoapp.bocauhoi.QuestionListActivity;
import com.example.duolingoapp.taikhoan.LoginActivity;
import com.example.duolingoapp.taikhoan.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    private DrawerLayout mDrawerLayout;

    private AppBarConfiguration mAppBarConfiguration;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar =findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        mDrawerLayout = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.HocTuVung){
            Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
            intent.putExtra("screenCurrent", "Học từ vựng");
            startActivity(intent);
        } else if (id == R.id.DienKhuyet){
            Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
            intent.putExtra("screenCurrent", "Điền khuyết");
            startActivity(intent);
        } else if (id == R.id.TracNghiem){
            Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
            intent.putExtra("screenCurrent", "Trắc nghiệm");
            startActivity(intent);
        } else if (id == R.id.LuyenNghe){
            Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
            intent.putExtra("screenCurrent", "Luyện nghe");
            startActivity(intent);
        } else if (id == R.id.SapXepCau){
            Intent intent = new Intent(MainActivity.this, QuestionListActivity.class);
            intent.putExtra("screenCurrent", "Sắp xếp câu");
            startActivity(intent);
        } else if (id == R.id.ThongTinTaiKhoan){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("hideLaunchScreen", true);
            startActivity(intent);
        }

        return true;
    }

}