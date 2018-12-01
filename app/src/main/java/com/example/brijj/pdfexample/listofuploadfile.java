package com.example.brijj.pdfexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listofuploadfile extends AppCompatActivity {

    DatabaseReference mDatabaseReference;
    ArrayList<Model> arrayList;
    RecyclerView recyclerView;
    MyAdapter adapter;

    public static final String DATABASE_PATH_UPLOADS = "uploads";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofuploadfile);
        recyclerView=findViewById(R.id.recycle);
        arrayList=new ArrayList<>();
        adapter=new MyAdapter(getApplicationContext(),arrayList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {   Model model=dataSnapshot.getValue(Model.class);
                arrayList.add(model);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      recyclerView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


          }
      });
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Model model=arrayList.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(model.getUri()));
                startActivity(intent);
            }
        });*/
    }


}
