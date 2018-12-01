package com.example.brijj.pdfexample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    ArrayList<Model> arrayList;
    Context context;
    private Model model;

    public MyAdapter(Context context, ArrayList<Model> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
         model=arrayList.get(position);
        holder.fname.setText(model.getFilename());
        holder.filepath.setText(model.getUri());
       holder.card.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setDataAndType(Uri.parse(model.getUri()),"application/pdf");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {   CardView card;
        TextView fname,filepath;
        public MyViewHolder(View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.f);
            filepath=itemView.findViewById(R.id.path);
            card=itemView.findViewById(R.id.card);
        }
    }
}
