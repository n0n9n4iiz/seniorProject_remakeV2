package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHisitem extends RecyclerView.Adapter<AdapterHisitem.ViewHolder> {

    private Context context;
    private ArrayList<DataGroupMM> dataGroupMMS;
    private int idlogin;
    FragmentTransaction fragmentTransaction;
    History fragmentpre;

    public AdapterHisitem(int idlogin,Context context, ArrayList<DataGroupMM> dataGroupMMS,History fragmentpre) {
        this.idlogin = idlogin;
        this.context = context;
        this.dataGroupMMS = dataGroupMMS;
        this.fragmentpre = fragmentpre;
    }


    @NonNull
    @Override
    public AdapterHisitem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new AdapterHisitem.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHisitem.ViewHolder holder, int position) {
            final DataGroupMM item = dataGroupMMS.get(position);

            holder.txtdate.setText(item.getDate()+"");

            holder.txtdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Context contextItem = view.getContext();
//                    Intent i = new Intent(contextItem,ListNoItem.class);
//                    i.putExtra("idlogin",idlogin);
//                    i.putExtra("Msel",item.getDate());
//                    contextItem.startActivity(i);
                    System.out.println(idlogin);
                    fragmentTransaction = fragmentpre.getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new ListHistory(idlogin,item.getDate())).addToBackStack(null);
                    fragmentTransaction.commit();
//                    view.getSupportActionBar().setTitle("Home");
                }
            });
    }

    @Override
    public int getItemCount() {
        return dataGroupMMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdate = (TextView) itemView.findViewById(R.id.txt_date);
        }
    }
}
