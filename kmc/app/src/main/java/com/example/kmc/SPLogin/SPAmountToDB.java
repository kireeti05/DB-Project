package com.example.kmc.SPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.CLogin.CollectorAmountDBToBen;
import com.example.kmc.Individual;
import com.example.kmc.NoteElements;
import com.example.kmc.PSAdapters.myadapterPS2;
import com.example.kmc.R;
import com.example.kmc.SPAdapters.myadapterSP2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.write.Label;

public class SPAmountToDB extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    myadapterSP2 adapter;
    String village1;

    Individual obj;
    String village2;
    List<DocumentSnapshot> list;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spamount_to_db);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }
        datalist=new ArrayList<>();
        adapter=new myadapterSP2(datalist,village1,village2);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {

                            obj=d.toObject(Individual.class);
                            if(!obj.getIndividualAmountRequired().equals("NA"))
                            {
                                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                    if(!obj.getSpApproved2().equals("yes") &&  !obj.getSpApproved2().equals("no"))
                                    {
                                        datalist.add(obj);
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);


    }

    public void search(View view) {
        Intent i = new Intent(this, SpAmountToDbSearch.class);
        i.putExtra("village1",village1);
        i.putExtra("village2",village2);
        startActivity(i);
    }
}