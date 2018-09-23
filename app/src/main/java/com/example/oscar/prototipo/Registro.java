package com.example.oscar.prototipo;

import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Registro extends AppCompatActivity {

    EditText txtnombre, txtemail, txttelefono, txtfecha_nacimiento, txtfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txttelefono = (EditText) findViewById(R.id.txttelefono);
        txtfecha_nacimiento = (EditText) findViewById(R.id.txtfecha_nacimiento);
        txtfoto=(EditText) findViewById(R.id.txtFoto);

    }

    public void Guardar(View v) {

        String nombre = txtnombre.getText().toString().trim();
        String email = txtemail.getText().toString().trim();
        String telefono = txttelefono.getText().toString().trim();
        String fecha_nacimiento = txtfecha_nacimiento.getText().toString().trim();
        String foto = txtfoto.getText().toString().trim();

        try{
            String urlParameters  = "nombre="+nombre+"&email="+email+"&telefono="+telefono+"&fecha_nacimiento="+fecha_nacimiento+"&foto="+foto;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = "https://bkndtg.herokuapp.com/usuarios/Usuario/";
            URL url = new URL( request );
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT","Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE","en-US,en;0.5");

            connection.setDoOutput(true);
            DataOutputStream dStream=new DataOutputStream(connection.getOutputStream());

            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();

            int responseCode=connection.getResponseCode();

            String output="Request URL "+url;
            output+=System.getProperty("line.separator")+" Request Parameters "+urlParameters;
            output+=System.getProperty("line.separator")+" Response Code "+responseCode;

            BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            StringBuilder responseOutput=new StringBuilder();

            while((line=br.readLine()) != null){
                responseOutput.append(line);
            }
            br.close();

            output+=System.getProperty("line.separator")+responseOutput.toString();

            System.out.println("----> "+output);

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Registro exitoso");
            alertDialog.show();

            txtfecha_nacimiento.setText("");
            txttelefono.setText("");
            txtemail.setText("");
            txtnombre.setText("");
            txtfoto.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
