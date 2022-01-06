package com.example.kalkulatorbagus;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kalkulatorbagus.adapter.KalkulatorAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText firstNumberEditText, secondNumberEditText;
    TextView txtHasil;
    RadioButton radioButton;
    RadioGroup radioGroup;
    Button btnHitung;

    SharedPreferences pref;
    Gson gson;
    RecyclerView rec_hasil;
    ArrayList<Kalkulator> listHasil;
    KalkulatorAdapter kalkulatorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec_hasil = findViewById(R.id.rec_hasil);

        rec_hasil.setLayoutManager(new LinearLayoutManager(this));

        pref = this.getSharedPreferences(getString(R.string.SHARE_KEY), Context.MODE_PRIVATE);
        gson = new GsonBuilder().create();

        firstNumberEditText = findViewById(R.id.firstNumberEditText);
        secondNumberEditText = findViewById(R.id.secondNumberEditText);
        txtHasil = findViewById(R.id.txtHasil);
        radioGroup = findViewById(R.id.radioGroup);
        btnHitung = findViewById(R.id.btnHitung);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hasil();

                String operasi = new String();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if(radioButton.getText().equals("Tambah")){
                    operasi = "+";
                }
                if(radioButton.getText().equals("Kurang")){
                    operasi = "-";
                }
                if(radioButton.getText().equals("Kali")){
                    operasi = "*";
                }
                if(radioButton.getText().equals("Bagi")){
                    operasi = "/";
                }

                String recordList = pref.getString(getString(R.string.RECORD_LIST), "[]");
                listHasil = gson.fromJson(recordList, new TypeToken<ArrayList<Kalkulator>>(){}.getType());
                if (listHasil == null) listHasil = new ArrayList<>();

                listHasil.add(new Kalkulator(firstNumberEditText.getText().toString(), secondNumberEditText.getText().toString(), txtHasil.getText().toString(), operasi));

                recordList = gson.toJson(listHasil);
                pref.edit().putString(getString(R.string.RECORD_LIST), recordList).apply();

                kalkulatorAdapter = new KalkulatorAdapter(listHasil);
                rec_hasil.setAdapter(kalkulatorAdapter);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // hapus saat resume
        Delete();
    }

    // menghapus data
    private void Delete(){
        SharedPreferences preferences = getSharedPreferences("Kalkulator", 0);
        preferences.edit().remove("RecordList").commit();
    }

    private void Tambah(){
        float a = Float.parseFloat(firstNumberEditText.getText().toString());
        float b = Float.parseFloat(secondNumberEditText.getText().toString());

        float hasil = a + b;
        Test(hasil);
    }

    private void Kurang(){
        float a = Float.parseFloat(firstNumberEditText.getText().toString());
        float b = Float.parseFloat(secondNumberEditText.getText().toString());

        float hasil = a - b;
        Test(hasil);
    }

    private void Kali(){
        float a = Float.parseFloat(firstNumberEditText.getText().toString());
        float b = Float.parseFloat(secondNumberEditText.getText().toString());

        float hasil = a * b;
        Test(hasil);
    }

    private void Bagi(){
        float a = Float.parseFloat(firstNumberEditText.getText().toString());
        float b = Float.parseFloat(secondNumberEditText.getText().toString());

        float hasil = a / b;
        Test(hasil);
    }

    private void Test(float hasil){
        int cek = (int) hasil;

        if(cek == hasil){
            txtHasil.setText(String.valueOf((int) hasil));
        }
        else{
            txtHasil.setText(String.valueOf(hasil));
        }
    }

    public void Hasil(){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.tambahRadioButton:
                Tambah();
                break;
            case R.id.kurangRadioButton:
                Kurang();
                break;
            case R.id.kaliRadioButton:
                Kali();
                break;
            case R.id.bagiRadioButton:
                Bagi();
                break;
        }
    }

    public void CheckButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Pilih: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

}