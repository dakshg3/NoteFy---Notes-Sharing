package com.example.notefy;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    ProgressDialog progressDialog;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Upload> mUploads;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        subject="";
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            subject =(String) b.get("Subject");
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new NotesAdapter(NotesActivity.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(NotesActivity.this);
        mStorage = FirebaseStorage.getInstance();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FileDb");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    if(upload.getCourse().equalsIgnoreCase(subject)) {
                        mUploads.add(upload);
                    }

                }
                if(mUploads.size()<1)
                {
                    Toast.makeText(NotesActivity.this, "No Notes Available for this Subject Yet", Toast.LENGTH_SHORT).show();
                }

                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });




    }


    @Override
    public void onItemClick(int position) {
        Intent intent= new Intent(getApplicationContext(),FileViewActivity.class);
        Upload x=mUploads.get(position);
        intent.putExtra("data",(Serializable) x);
        startActivity(intent);

    }

    @Override
    public void onWhatEverClick(int position) {
        file_download(mUploads.get(position).getfileUrl(),position);

    }


    public void file_download(String uRl,int pos) {

        File direct = new File(Environment.getExternalStorageDirectory().getPath(),"abc/");
        Boolean x=false;
        if (!direct.exists()) {
            x =direct.mkdirs();
        }
        else
        {
            x=true;
        }

        if(x){
            DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(uRl);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri);

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true).setTitle("File")
                    .setDescription("Files")
                    .setDestinationInExternalPublicDir("/NoteFy", mUploads.get(pos).getName());

            mgr.enqueue(request);
            Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "not created", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getfileUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(NotesActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }


}
