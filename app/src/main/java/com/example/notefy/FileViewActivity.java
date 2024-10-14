package com.example.notefy;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FileViewActivity extends AppCompatActivity implements Serializable {

    String url,author;
    FirebaseDatabase database;
    RatingBar ratingbar;
    Button button;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Rating> mRatings;
    private Upload y;
    int counter;
    double sum,temp;
    TextView t4;
    Boolean var;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        counter=0;
        sum=0.0;
        var=true;
        mRatings = new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Ratings");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRatings.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Rating r = postSnapshot.getValue(Rating.class);
                    r.setKey(postSnapshot.getKey());
                    try {
                        if (r.getFileName().equalsIgnoreCase(y.getName())) {
                            mRatings.add(r);
                            counter++;
                        }
                        if (r.getAuthor().equalsIgnoreCase(author) && r.getFileName().equalsIgnoreCase(y.getName())) {
                            temp=r.getRating();
                            var=false;
                            ratingbar.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    return true;
                                }
                            });
                        }

                    }
                    catch (Exception e)
                    {
                        //Toast.makeText(FileViewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                try{
                    for(int j=0;j<counter;j++)
                    {
                        sum=sum+mRatings.get(j).getRating();
                        //Toast.makeText(FileView.this,Double.toString(sum), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    //Toast.makeText(FileViewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                double res=sum/counter;
                double valueRounded = Math.round(res * 100D) / 100D;
                String res1=Double.toString(valueRounded);
                t4.setText(res1+"/5.0");

                float x=Float.valueOf(Double.toString(temp));
                ratingbar.setRating(x);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FileViewActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                counter=0;
                sum=0.0;
            }
        });

        y=(Upload) getIntent().getSerializableExtra("data");
        TextView t=(TextView) findViewById(R.id.desc);
        TextView t1=(TextView) findViewById(R.id.author);
        TextView t2=(TextView) findViewById(R.id.filename);
        TextView t3=(TextView) findViewById(R.id.course);
        t4=(TextView) findViewById(R.id.TotalRating);
        Button dwnld= (Button) findViewById(R.id.download);
        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
        button=(Button)findViewById(R.id.rate);

        t.setText(y.getDesc());
        t1.setText(y.getAuthor());
        t2.setText(y.getName());
        t3.setText(y.getCourse());
        url=y.getfileUrl();

        dwnld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_download(url);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(var){
                    String rating=String.valueOf(ratingbar.getRating());
                    Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                    DatabaseReference reference=database.getReference("Ratings");
                    Rating rating1= new Rating(y.getName(),author,ratingbar.getRating());
                    String uploadId = reference.push().getKey();
                    reference.child(uploadId).setValue(rating1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FileViewActivity.this, "Rating Sent", Toast.LENGTH_SHORT).show();
                                counter=0;
                                sum=0.0;
                            }
                            else{
                                Toast.makeText(FileViewActivity.this, "Rating Failed", Toast.LENGTH_SHORT).show();
                                counter=0;
                                sum=0.0;
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(FileViewActivity.this, "You have already rated this File", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("518344026106-bbdpipgoticekrdv8hf1hshoi9mgr5a6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        author=currentUser.getDisplayName();
        if (!isNetworkConnected()) {
            Toast.makeText(this, "Not Connected to the Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void file_download(String uRl) {

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
                    .setDestinationInExternalPublicDir("/NoteFy", y.getName());

            mgr.enqueue(request);
            Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "not created", Toast.LENGTH_SHORT).show();
        }
    }
}
