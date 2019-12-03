package com.example.myapplication;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {
    View v;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Spinner spinner_year;
    private Spinner spinner_mm;

    private List<Datayear> data;
    private ArrayList<Datayear> datayear = new ArrayList<Datayear>();
    private ArrayAdapter<Datayear> adapteryear;

    private  List<Datamonth> datamonths;
    private ArrayList<Datamonth> datamm = new ArrayList<Datamonth>();
    private ArrayAdapter<Datamonth> adaptermm;

    private ArrayList<DataGroupMM> dataGroupMMS = new ArrayList<>();
    private AdapterHisitem adapterHisitem;
    private RecyclerView his_Recyview;

    private ArrayList<String> formatMM;

    private int idlogin;
    int hn;
    public History() {
        // Required empty public constructor
    }
    public History(int idlogin,int hn) {
        // Required empty public constructor
        this.idlogin = idlogin;
        this.hn = hn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_history, container, false);
        spinner_year = (Spinner) v.findViewById(R.id.spinneryear);
        spinner_mm = (Spinner) v.findViewById(R.id.spinnermm);
        getYearToSpinnerAdapter();


        his_Recyview = (RecyclerView) v.findViewById(R.id.hisRecy);
        his_Recyview.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
    public void getYearToSpinnerAdapter(){
        adapteryear = new ArrayAdapter<Datayear>(getContext(),android.R.layout.simple_dropdown_item_1line,datayear );
        Call<List<Datayear>> call = api.getYear(idlogin);
        call.enqueue(new Callback<List<Datayear>>() {
            @Override
            public void onResponse(Call<List<Datayear>> call, Response<List<Datayear>> response) {
                data = response.body();
//                datayear = new ArrayList<>(response.body());
                for(Datayear d : data){
                    datayear.add(new Datayear(d.getYear()));
                    spinner_year.setAdapter(adapteryear);
                }
            }
            @Override
            public void onFailure(Call<List<Datayear>> call, Throwable t) {
            }
        });



        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final Datayear textyear = (Datayear) adapterView.getSelectedItem();
                System.out.println(textyear.getYear());

                final Context context = view.getContext();
                adaptermm = new ArrayAdapter<Datamonth>(context,android.R.layout.simple_dropdown_item_1line,datamm );
                adaptermm.clear();
                // spinnermm follow spineryear
                //add connect month later
                Call<List<Datamonth>> call = api.getMonth(idlogin,textyear.getYear());
                call.enqueue(new Callback<List<Datamonth>>() {

                    @Override
                    public void onResponse(Call<List<Datamonth>> call, Response<List<Datamonth>> response) {
                        datamonths = response.body();
                        for(Datamonth d : datamonths){
                            String formatmonth = "";
                            switch (d.getMonth()){
                                case "01" : formatmonth = "มกราคม"; break;
                                case "02" : formatmonth = "กุมภาพันธ์"; break;
                                case "03" : formatmonth = "มีนาคม"; break;
                                case "04" : formatmonth = "เมษายน"; break;
                                case "05" : formatmonth = "พฤษภาคม"; break;
                                case "06" : formatmonth = "มิถุนายน"; break;
                                case "07" : formatmonth = "กรกฎาคม"; break;
                                case "08" : formatmonth = "สิงหาคม"; break;
                                case "09" : formatmonth = "กันยายน"; break;
                                case "10" : formatmonth = "ตุลาคม"; break;
                                case "11" : formatmonth = "พฤศจิกายน"; break;
                                case "12" : formatmonth = "ธันวาคม"; break;
                            }
                            datamm.add(new Datamonth(d.getMonth(),formatmonth));
                            spinner_mm.setAdapter(adaptermm);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Datamonth>> call, Throwable t) {

                    }
                });


                spinner_mm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> adapterView, final View view, int i, long l) {
                        final Datamonth textmonth = (Datamonth) adapterView.getSelectedItem();
                        System.out.println(textmonth.getMonth());
                        final Context c2 = view.getContext();
                        Call<List<DataGroupMM>> call = api.getGroupMM(idlogin,textmonth.getMonth(),textyear.getYear());
                        call.enqueue(new Callback<List<DataGroupMM>>() {
                            @Override
                            public void onResponse(Call<List<DataGroupMM>> call, Response<List<DataGroupMM>> response) {
                                dataGroupMMS = new ArrayList<>(response.body());
                                for (DataGroupMM d : dataGroupMMS
                                ) {
                                    System.out.println("aaa "+d.getDate());
                                }
                                adapterHisitem = new AdapterHisitem(idlogin,c2,dataGroupMMS,History.this);
                                his_Recyview.setAdapter(adapterHisitem);
                            }

                            @Override
                            public void onFailure(Call<List<DataGroupMM>> call, Throwable t) {

                            }
                        });


                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
