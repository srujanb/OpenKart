package com.example.sbarai.openkart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.ProspectOrderDetails;
import com.example.sbarai.openkart.R;
import com.example.sbarai.openkart.Utils.FirebaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by sbarai on 2/8/18.
 */

public class RvProspectOrderAdapter extends RecyclerView.Adapter<RvProspectOrderAdapter.MyViewHolder>{


    private LayoutInflater inflater;
    private List<String> data = Collections.EMPTY_LIST;
    private Context context;

    public RvProspectOrderAdapter(Context context, List<String> data){
        inflater = LayoutInflater.from(context);
        this.context = context;
        if (data.size() == 0)
            this.data = new ArrayList<>();
        else
            this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_rv_prospect_order,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.storeTitle.setText("Loading...");
        setContentToHolder(holder,position,1);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOrderDetails();
            }
        });
    }

    private void goToOrderDetails() {
        Intent intent = new Intent(context, ProspectOrderDetails.class);
        context.startActivity(intent);
    }

    public void setContentToHolder(final MyViewHolder holder,final int position, final int attempt){
        String key = data.get(position);
        DatabaseReference ref = FirebaseManager.getRefToSpecificProspectOrder(key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProspectOrder oder = dataSnapshot.getValue(ProspectOrder.class);
                holder.storeTitle.setText(oder.getDesiredStore());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (attempt < 3){
                    setContentToHolder(holder,position,attempt + 1);
                }else{
                    data.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void insertIntoData(String key) {
        data.add(key);
        this.notifyDataSetChanged();
        Log.d("TAGG","Notifying dataset changed");
    }

    public void removeFromData(String key) {
        data.remove(key);
        Log.d("TAGG","removed from data, new size" + data.size());
        this.notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView storeTitle;
        View rootView;
        public MyViewHolder(View itemView) {
            super(itemView);
            storeTitle = itemView.findViewById(R.id.storeName);
            rootView = itemView.findViewById(R.id.root);
        }
    }
}
