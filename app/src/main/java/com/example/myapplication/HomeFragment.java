package com.example.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    CarouselView  carouselView;
    private int[] img = new int[]{
            R.drawable.img1,R.drawable.img2,R.drawable.img3
    };
    private String[] title = new String[]{
            "pic1","pic2","pic3"
    };
    CardView map,myact,appointment,history;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    int idlogin;
    int hn;
    public HomeFragment() {
        // Required empty public constructor
    }
    public HomeFragment(int idlogin,int hn) {
        // Required empty public constructor
        this.idlogin = idlogin;
        this.hn = hn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //-------------carousel
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = v.findViewById(R.id.caro);
        carouselView.setPageCount(img.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(img[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                String url = "https://www.google.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                Toast.makeText(getContext(),title[position],Toast.LENGTH_SHORT).show();
            }
        });
        //-------------Cardview click
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        map = (CardView) v.findViewById(R.id.map_btn);
        myact = (CardView) v.findViewById(R.id.myact_btn);
        appointment = (CardView) v.findViewById(R.id.appoint_btn);
        history = (CardView) v.findViewById(R.id.history_btn);

        map.setOnClickListener(this);
        myact.setOnClickListener(this);
        appointment.setOnClickListener(this);
        history.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.map_btn :

                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new SettingFragment()).addToBackStack(null);
                fragmentTransaction.commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Map");
            break;

            case R.id.myact_btn :
                System.out.println(idlogin);
                System.out.println(hn);
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MyActivity(idlogin,hn)).addToBackStack(null);
                fragmentTransaction.commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Activity");

            break;
            case R.id.appoint_btn :
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MeetingDate(idlogin,hn)).addToBackStack(null);
                fragmentTransaction.commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Appointment");
                break;

            case R.id.history_btn :
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new History(idlogin,hn)).addToBackStack(null);
                fragmentTransaction.commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("History");
                break;
        }
    }

}
