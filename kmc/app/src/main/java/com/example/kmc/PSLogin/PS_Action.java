package com.example.kmc.PSLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kmc.Individual;

import com.example.kmc.R;
import com.example.kmc.login.Collector_Login;
import com.example.kmc.login.SOLogin;
import com.example.kmc.login.SPLogin;
import com.google.firebase.firestore.FirebaseFirestore;

public class PS_Action extends AppCompatActivity implements View.OnClickListener {

    public CardView card1,card2,card3,card4, card5;
    String village;
    String district;
    String mandal;
    int DbPending;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps_action);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);
        TextView t1=(TextView) findViewById(R.id.DbPending);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village");
            village=value;
            mandal=extras.getString("mandal");
            district=extras.getString("district");
            //The key argument here must match that used in the other activity

        }else{
            Log.d("extra", "no");
        }
        Individual obj = new Individual();
        
        if(obj.getVillage().equals(village)&&obj.getStatus().equals("Sp Approved")) {
            DbPending++;
        }

    }
    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.c1:
                i = new Intent(this, PSAddEdit.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, PSAmountToDB.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, PSAmountDBToBen.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, PSGrounding.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
            case R.id.c5:
                i = new Intent(this, PSMasterReport.class);
                i.putExtra("village",village);
                i.putExtra("mandal",mandal);
                i.putExtra("district",district);
                startActivity(i);
                break;
        }


    }
}