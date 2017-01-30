package com.example.personal.diapositivasapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button verFiles = (Button)findViewById(R.id.button_ver_files);
        verFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, PickFileWithOpenerActivity.class);
                startActivity(intent);
            }
        });
        Button verFile2 = (Button)findViewById(R.id.verfile);
        verFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, RetrieveMetadaActivity.class);
                startActivity(intent);
            }
        });
    }
}
