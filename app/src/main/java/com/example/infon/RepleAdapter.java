package com.example.infon;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepleAdapter extends RecyclerView.Adapter<RepleAdapter.ViewHolder>
{
    private final ArrayList<Reple> repleArrayList;
    public RepleAdapter(Activity activity, ArrayList<Reple> repleArrayList)
    {
        this.repleArrayList =repleArrayList;
    }

    @NonNull
    @Override
    public RepleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reple_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RepleAdapter.ViewHolder holder, int position)
    {
        Reple reple = repleArrayList.get(position);


        holder.reple.setText(reple.getContents());
    }

    @Override
    public int getItemCount()
    {
        return repleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView reple;
        public ViewHolder(@NonNull View view)
        {
            super(view);

            reple= view.findViewById(R.id.reple);
        }
    }
}
