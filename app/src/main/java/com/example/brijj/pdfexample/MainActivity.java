package com.example.brijj.pdfexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    public static final String PATH_FOR_STORAGE = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    final static int REQUEST_CODE = 2342;
    TextView status;
    Button viewupload;
    EditText filename;
    ProgressBar progressBar;
    Button uploadbutton;
    StorageReference mStorageReference;
   DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
        status = (TextView) findViewById(R.id.textViewStatus);
        filename = (EditText) findViewById(R.id.filename);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        uploadbutton=findViewById(R.id.buttonUploadFile);
        uploadbutton.setOnClickListener(this);
        status.setOnClickListener(this);
        viewupload=findViewById(R.id.textViewUploads);
        viewupload.setOnClickListener(this);
    }


    private void getPDF() {

        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
              upload(data.getData());
            }else{
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upload(Uri data) {
        if(data==null)
        {
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
            return;
        }
        if(filename.getText().toString().trim()==null)
        {
            Toast.makeText(MainActivity.this, "Please enter file name", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        StorageReference Ref = mStorageReference.child(PATH_FOR_STORAGE + System.currentTimeMillis());
        Ref.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        status.setText("Uploaded Successfully");


                        Model upload = new Model(filename.getText().toString(), taskSnapshot.getDownloadUrl().toString());

                      mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    /*@SuppressWarnings("VisibleForTests")*/
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        status.setText((int) progress + "% Uploading...");
                    }
                });

    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.buttonUploadFile:
                if(filename.getText().toString().trim()==null)
                {
                    Toast.makeText(this, "Please enter filename", Toast.LENGTH_SHORT).show();
                }
                else {
                    getPDF();
                }
                break;
            case R.id.textViewUploads:
                startActivity(new Intent(this, listofuploadfile.class));
                break;
        }

    }

}

