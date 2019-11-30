package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
public class SelectCurrentLocation extends Fragment {
    View v;
    private ArrayList<String> detailofPiont = new ArrayList<String>();
    //set api
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://obscure-refuge-47108.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PointService api = retrofit.create(PointService.class);

    //scan_btn
    private Button confirm_btn;
    private ImageButton scan_btn;

    //listspin
    private ArrayList<String> listSpin = new ArrayList<String>();
    //    private ArrayList<Integer> idofListSpin = new ArrayList<Integer>();
    private Spinner curentLocationSpinner;

    int startPointID;
    int End;
    String nameRoomEndFromList;
    String room;
    int endfromMap;
    String activitys;
    FragmentTransaction fragmentTransaction;
    Dijkstra a = new Dijkstra();
    Drawline d = new Drawline();
    Splash s = new Splash();
    public SelectCurrentLocation() {
        // Required empty public constructor
    }
    public SelectCurrentLocation(String room) {
        this.room = room;
    }
    public SelectCurrentLocation(int endfromMap) {
        this.endfromMap = endfromMap;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_select_current_location, container, false);

        curentLocationSpinner = (Spinner) v.findViewById(R.id.currentlocationSpin);
        //getDetail();
        //QR
        scan_btn = v.findViewById(R.id.scanqrBtn);
        confirm_btn=(Button) v.findViewById(R.id.confirmBtn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanQR();
            }
        });
        loadSpiinerData();
//        bundle = getIntent().getExtras();
//        listSpin = bundle.getStringArrayList("listSpin");
//        System.out.println(bundle.getString("room"));
        if(room!=null) {
            nameRoomEndFromList = room;
            getIdFromEndSpinner(nameRoomEndFromList);
        }
        else {
            End= endfromMap;
        }

        curentLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        "Select : " + curentLocationSpinner.getSelectedItem(),
                        Toast.LENGTH_SHORT).show();
                String y = curentLocationSpinner.getSelectedItem().toString();
                getIdFromStartSpinner(y);
        a.setNewData(null);
        a.setNewData2(null);
        d.setPath(null);
        d.setNewData2(null);
        d.setDirectAr(null);
        d.setDirectAr2(null);
        d.setDistancefordirect(null);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MapFromList(startPointID,End)).addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(SelectCurrentLocation.this, MapFromList.class);
//                intent.putExtra("START",startPointID  );
//                intent.putExtra("END",End  );
//                startActivity(intent);


            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();

                startPointID = Integer.parseInt(result.getContents());
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MapFromList(startPointID,End)).addToBackStack(null);
                fragmentTransaction.commit();
//                Intent intent = new Intent(SelectCurrentLocation.this, MapFromList.class);
//                intent.putExtra("START",startPointID  );
//                intent.putExtra("END",End  );
//                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    IntentIntegrator integrator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        integrator  = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity

    }
    //start scan
//    final Activity activity = getActivity();
    public void startScanQR() {

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    public void loadSpiinerData() {

        Call<List<Demo.Contributor>> call = api.getRoom("1");
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("Roomapi" + d.p_name + "jaaa");
                    listSpin.add(d.p_name);
//                    idofListSpin.add(d.p_id);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,s.getListSpin());
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    curentLocationSpinner.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }

        });
    }
    public void getIdFromStartSpinner(String p_name) {

        Call<List<Demo.Contributor>> call = api.getPointID(p_name);
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("idapi" + d.p_id + "jaaa");

                    startPointID = d.p_id;
                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }
        });

    }

    public void getIdFromEndSpinner(String p_name) {

        Call<List<Demo.Contributor>> call = api.getPointID(p_name);
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("idapi" + d.p_id + "jaaa");

                    End = d.p_id;
                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call detail fffffffffffffffffffffffffffffailed");
            }
        });

    }

    public void getDetail(){
        Call<List<Demo.Contributor>> call = api.getPointInfo();
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    detailofPiont.add(d.p_detail_for_direction);
                    System.out.println("call detail ssssssssssssssssss");

                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }

        });
    }

}
