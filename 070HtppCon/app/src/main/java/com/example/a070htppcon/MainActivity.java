/*
Autor: Hermosa Programación - http://www.hermosaprogramacion.com

Artículo: Operaciones HTTP en Android con el cliente HttpURLConnection

Objetivos:  - Comprender el uso del cliente HttpURLConnection
            - Transmitir información con los métodos GET y POST
 */

package com.example.a070htppcon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    /*
    Lista de comentarios
     */
    ListView comments;

    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener la instancia de la lista
        comments = (ListView)findViewById(R.id.CommentsList);

        /*
        Comprobar la disponibilidad de la Red
         */
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                new GetCommentsTask().
                        execute(
                                new URL("http://monstalkers.hostoi.com/data/get_all_comments.php"));
            } else {
                Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            startActivity(new Intent(this, FormActivity.class));
        }
        if (id == R.id.action_update) {
            try {
                new GetCommentsTask().
                        execute(
                                new URL("http://monstalkers.hostoi.com/data/get_all_comments.php"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    La clase GetCommentsTask representa una tarea asincrona que realizará
    las operaciones de red necesarias en segundo plano para obtener toda la
    lista de comentarios alojada en el servidor.
     */
    public class GetCommentsTask extends AsyncTask<URL, Void, List<String>> {

        @Override
        protected List<String> doInBackground(URL... urls) {

            List<String> comments = null;

            try {

                // Establecer la conexión
                con = (HttpURLConnection)urls[0].openConnection();

                // Obtener el estado del recurso
                int statusCode = con.getResponseCode();

                if(statusCode!=200) {
                    comments = new ArrayList<>();
                    comments.add("El recurso no está disponible");
                    return comments;
                }
                else{

                    /*
                    Parsear el flujo con formato JSON a una lista de Strings
                    que permitan crean un adaptador
                     */
                    InputStream in = new BufferedInputStream(con.getInputStream());

                    JSONCommentsParser parser = new JSONCommentsParser();

                    comments = parser.readJsonStream(in);

                }

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                con.disconnect();
            }
            return comments;

        }

        @Override
        protected void onPostExecute(List<String> s) {

            /*
            Se crea un adaptador con el el resultado del parsing
            que se realizó al arreglo JSON
             */
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getBaseContext(),
                    android.R.layout.simple_list_item_1,
                    s);

            // Relacionar adaptador a la lista
            comments.setAdapter(adapter);
        }
    }

}
