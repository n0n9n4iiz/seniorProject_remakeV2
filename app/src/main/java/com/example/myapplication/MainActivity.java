package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
DrawerLayout drawerLayout;
Toolbar toolbar;
ActionBarDrawerToggle actionBarDrawerToggle;
FragmentTransaction fragmentTransaction;
NavigationView navigationView;
    int idlogin;
    int hn ;
    String name = "";
    View headerView;
    TextView tname,hnn;
    ArrayList<Data> arrHN = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        idlogin = bundle.getInt("idlogin");
        checkHN(idlogin);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new HomeFragment(idlogin,hn));
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView = findViewById(R.id.navigationview);
        headerView = navigationView.getHeaderView(0);
        tname = (TextView) headerView.findViewById(R.id.name);
        hnn = (TextView) headerView.findViewById(R.id.hnnumber);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home_id :
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new HomeFragment(idlogin,hn)).addToBackStack(null);
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Home");
//                    menuItem.setChecked(true);
                    menuItem.setCheckable(false);
                    drawerLayout.closeDrawers();

                    break;
                    //for map
                    case R.id.set_id : fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new MainMap()).addToBackStack(null);
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Map");
//                    menuItem.setChecked(true);
                    menuItem.setCheckable(false);
                    drawerLayout.closeDrawers();
                    break;

                    case R.id.other_id :
                    System.out.println(idlogin);
                    System.out.println(hn);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new MyActivity(idlogin,hn)).addToBackStack(null);
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("My Activity");
//                    menuItem.setChecked(true);
                    menuItem.setCheckable(false);
                    drawerLayout.closeDrawers();
                    break;

                    case R.id.maa :
                        System.out.println(idlogin);
                        System.out.println(hn);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new AddnewActivity(idlogin,hn)).addToBackStack(null);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Make an appointment");
//                        menuItem.setChecked(true);
                        menuItem.setCheckable(false);
                        drawerLayout.closeDrawers();

                        break;
                    case R.id.logout :
                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
//                        menuItem.setChecked(true);
                        menuItem.setCheckable(false);
                        drawerLayout.closeDrawers();
                        CustomIntent.customType(MainActivity.this,"right-to-left");

                        break;
                }
                return true;
            }
        });


    }
    public void checkHN(int id){

        Call<List<Data>> call = api.getHN(id);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                arrHN = new ArrayList<>(response.body());
                for (Data d : arrHN) {
                    hn = d.getHn();
                    name = d.getFirstname()+" "+d.getLastname();

                    tname.setText(name);
                    hnn.setText(String.valueOf(hn));
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

}
