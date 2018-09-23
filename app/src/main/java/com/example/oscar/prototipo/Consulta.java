package com.example.oscar.prototipo;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Consulta extends AppCompatActivity {

    ArrayList<Usuario> users;
    private ArrayAdapter<String> adaptador;
    private ListView usuariosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        System.out.println("//////ESTA POR ACA");

        leerJsonOnline();

        ArrayList<String> datos=new ArrayList<String>();
        for (int i = 0; i < users.size(); i++) {
            datos.add("Nombre: "+users.get(i).getNombre()+"\nEmail: "+users.get(i).getEmail()+"\nTelefono: "+users.get(i).getTelefono()+"\nFecha Nacimiento: "+users.get(i).getFecha_nacimiento()+"\nFoto: "+users.get(i).getFoto());
        }

        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        usuariosList=(ListView)findViewById(R.id.listView);
        usuariosList.setAdapter(adaptador);

        usuariosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

    }

    private void leerJsonOnline() {
        try {

            users = new ArrayList<>();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = null;
            HttpURLConnection conn;

            url = new URL("https://bkndtg.herokuapp.com/usuarios/Usuario/?format=json");
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            json = response.toString();

            JSONArray jarrau = null;

            jarrau = new JSONArray(json);


            for (int i = 0; i < jarrau.length(); i++) {
                Usuario usuario=new Usuario();
                JSONObject object = jarrau.getJSONObject(i);
                usuario.setNombre(object.getString("nombre"));
                usuario.setEmail(object.getString("email"));
                usuario.setTelefono(object.getString("telefono"));
                usuario.setFecha_nacimiento(object.getString("fecha_nacimiento"));
                usuario.setFoto(object.getString("foto"));
                users.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
