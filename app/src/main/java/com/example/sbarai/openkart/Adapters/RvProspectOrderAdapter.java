package com.example.sbarai.openkart.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sbarai.openkart.Models.ProspectOrder;
import com.example.sbarai.openkart.R;

import java.util.Collections;
import java.util.List;


/**
 * Created by sbarai on 2/8/18.
 */

public class RvProspectOrderAdapter extends RecyclerView.Adapter<RvProspectOrderAdapter.MyViewHolder>{


    private LayoutInflater inflater;
    List<ProspectOrder> data = Collections.EMPTY_LIST;

    public RvProspectOrderAdapter(Context context, List<ProspectOrder> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_rv_prospect_order,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProspectOrder oder = data.get(position);
        holder.storeTitle.setText(oder.getDesiredStore());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView storeTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            storeTitle = itemView.findViewById(R.id.storeName);
        }
    }
}
