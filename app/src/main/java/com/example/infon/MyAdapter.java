package com.example.infon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private final ArrayList<NationDto> itemArrayList;
    public MyAdapter(Activity activity, ArrayList<NationDto> itemArrayList)
    {
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position)
    {
        NationDto nationDto = itemArrayList.get(position);


        holder.nationName.setText(nationDto.getName());
        holder.nationCapital.setText(nationDto.getCapital());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.details.getContext();
                Intent i = new Intent(context,NationDetailsActivity.class);
                i.putExtra("name",nationDto.getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nationName,nationCapital;
        Button details;
        public ViewHolder(@NonNull View view)
        {
            super(view);

             nationName= view.findViewById(R.id.nationName);
            nationCapital= view.findViewById(R.id.nationCapital);
            details = view.findViewById(R.id.details);
        }
    }
}