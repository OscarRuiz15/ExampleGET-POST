package com.example.oscar.prototipo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnRegistrar, btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        btnConsultar=(Button)findViewById(R.id.btnConsultar);

    }

    public void Registro(View v){
        Intent intent = new Intent(MainActivity.this, Registro.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void Consulta(View v){
        Intent intent = new Intent(MainActivity.this, Consulta.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
