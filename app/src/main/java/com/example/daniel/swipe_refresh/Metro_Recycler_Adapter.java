package com.example.daniel.swipe_refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Metro_Recycler_Adapter extends RecyclerView.Adapter<Metro_Recycler_Adapter.ViewHolder> {

    private Context context;
    private List<Metro_Cons> list;

    public Metro_Recycler_Adapter (List<Metro_Cons> list, Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_insertdesign,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Metro_Cons metro_GET=list.get(position);
        viewHolder.mtxtStation.setText(metro_GET.getStation());
        viewHolder.mtxtDestination.setText(metro_GET.getDestination());
        viewHolder.mtxtTime.setText(metro_GET.getArrive_time());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mtxtStation, mtxtDestination, mtxtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mtxtStation=(TextView)itemView.findViewById(R.id.txtStation);
            mtxtDestination=(TextView)itemView.findViewById(R.id.txtDestionation);
            mtxtTime=(TextView)itemView.findViewById(R.id.txtTime);
        }
    }
}
