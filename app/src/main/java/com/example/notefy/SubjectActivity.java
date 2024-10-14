package com.example.notefy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SubjectActivity extends AppCompatActivity implements SubjectAdapter.OnItemClickListener{

    int deptnum,idd;
    Spinner mySpinner;
    private RecyclerView mRecyclerView;
    private SubjectAdapter mAdapter;
    private String[] mSub,mSub1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        deptnum=0;
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            deptnum =(Integer)(b.get("deptnumber"));
        }

        mySpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SubjectActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.semester));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String y=Integer.toString(position+1)+Integer.toString(deptnum);
                idd=SubjectActivity.this.getResources().getIdentifier("semester"+y,"array",SubjectActivity.this.getPackageName());
                mSub1=new String[30];
                mSub=getResources().getStringArray(idd);

                for(int i=0;i<mSub.length;i++){
                    String[] temp= mSub[i].split(" ");
                    mSub1[i]=" ";
                    for(int j=0;j<temp.length;j++) {
                        Character s = temp[j].charAt(0);
                        if (!s.toString().equals(s.toString().toLowerCase())) {
                            mSub1[i] = mSub1[i] + s;
                        }
                    }
                }
                mRecyclerView = findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(SubjectActivity.this));
                mAdapter = new SubjectAdapter(SubjectActivity.this, mSub,mSub1);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(SubjectActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(SubjectActivity.this,NotesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("Subject",mSub[position]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
