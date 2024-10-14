package com.example.notefy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;

public class UploadActivity extends AppCompatActivity implements Serializable {

    Button selectfile,upload;
    TextView notification;
    Uri fileuri;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseStorage storage;
    StorageReference mchild;
    FirebaseDatabase database;
    String url,text,text1,desc;
    int temp;
    Spinner mySpinner0;
    Spinner mySpinner1;
    Spinner mySpinner2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(UploadActivity.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    Intent in = new Intent(UploadActivity.this,AboutUs.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.local:
                    Intent intent = new Intent(UploadActivity.this,Downloads.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        mySpinner0 = (Spinner) findViewById(R.id.spinner0);
        mySpinner1 = (Spinner) findViewById(R.id.spinner1);
        mySpinner2 = (Spinner) findViewById(R.id.spinner2);



        ArrayAdapter<String> myAdapter0 = new ArrayAdapter<String>(UploadActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.dept));
        myAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner0.setAdapter(myAdapter0);

        mySpinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            temp=i;
                ArrayAdapter < String > myAdapter1 = new ArrayAdapter<String>(UploadActivity.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.semester));
                myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner1.setAdapter(myAdapter1);

                mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int k, long id) {
                        for(int j=1;j<9;j++)
                        {
                            if(j==k+1)
                            {
                                String y=Integer.toString(j)+Integer.toString(temp);
                                int idd=UploadActivity.this.getResources().getIdentifier("semester"+y,"array",UploadActivity.this.getPackageName());
                                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(UploadActivity.this,
                                        android.R.layout.simple_list_item_1, getResources().getStringArray(idd));
                                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mySpinner2.setAdapter(myAdapter2);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



/*
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UploadActivity.this,
        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.semester));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(myAdapter);

        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                for(int j=1;j<9;j++) {
                    if (j == i+1) {
                        String x=Integer.toString(j)+"0";
                        int z=Integer.parseInt(x);
                        String g="semester"+z;
                        int idd=UploadActivity.this.getResources().getIdentifier(g,"array",UploadActivity.this.getPackageName());
                        Toast.makeText(UploadActivity.this, "x", Toast.LENGTH_SHORT).show();
                        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(UploadActivity.this,
                                android.R.layout.simple_list_item_1, getResources().getStringArray(idd));
                        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySpinner2.setAdapter(myAdapter1);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        selectfile=findViewById(R.id.selectFile);
        upload=findViewById(R.id.upload);
        notification=findViewById(R.id.notification);


        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    selectfile();
                }
                else
                {
                    ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = mySpinner1.getSelectedItem().toString();
                text1 = mySpinner2.getSelectedItem().toString();
                EditText ed=(EditText) findViewById(R.id.desc);
                desc=ed.getText().toString().trim();
                if(fileuri!=null && text!=null && text1!=null && desc!=null)
                    uploadfile(fileuri);
                else
                    Toast.makeText(UploadActivity.this,"Select a File and Course and add Description",Toast.LENGTH_SHORT).show();
            }


        });


    }

    private void uploadfile(Uri fileuri) {

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final String filename1=fileuri.getLastPathSegment().toString();//.replace(".", ",");
        final StorageReference storageReference=storage.getReference();
        storageReference.child("uploads").child(filename1).putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mchild=storageReference.child("uploads").child(filename1);
                mchild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url=uri.toString();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String username = user.getDisplayName();
                        DatabaseReference reference=database.getReference("FileDb");
                        Upload upload = new Upload(filename1,url,text1,text,desc,username);
                        String uploadId = reference.push().getKey();
                        reference.child(uploadId).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UploadActivity.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    notification.setText("File Uploaded");
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this,"File not Uploaded",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress((int) progress);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectfile();
        }
        else
        {
            Toast.makeText(UploadActivity.this,"Please Provide Permission",Toast.LENGTH_SHORT).show();
        }


    }

    private void selectfile() {
        Intent intent =new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==86 && resultCode==RESULT_OK && data!=null)
        {
            fileuri=data.getData();
            notification.setText(fileuri.getLastPathSegment().toString());
        }
        else
        {
            Toast.makeText(UploadActivity.this,"Please Select File",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
