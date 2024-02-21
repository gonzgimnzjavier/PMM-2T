package com.example.a070htppcon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class FormActivity extends AppCompatActivity {

    /*
    Instancia del EditText
     */
    EditText body ;

    /*
    Comentario contenido
     */
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Obtener instancia del edit text
        body = (EditText)findViewById(R.id.CommentBody);
    }


    public void onClickSend (View v) throws MalformedURLException {

        // Obtener el valor del comentario
        comment = body.getText().toString();

        /*
        Se comprueba la validez del comentario para no tener
        valores irregulares
         */
        if (comment==null || comment.compareTo("")==0)
            Toast.makeText(this, "Escriba un comentario",Toast.LENGTH_LONG).show();
        else {

            /*
            Comprobar la disponibilidad de la Red
             */
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // Iniciar Tarea asícrona
                new PostCommentTask().
                        execute(
                                new URL("http://monstalkers.hostoi.com/data/insert_comments.php"));

                // Finalizar actividad
                finish();
            }
            else{
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void onClickCancel (View v) {
        // Finalizar actividad
        finish();
    }

    /*
    La clase PostCommentTask permite enviar los datos hacia el servidor
    para guardar el comentario del usuario.
     */
    public class PostCommentTask extends AsyncTask<URL, Void, Void> {

        @Override
        protected Void doInBackground(URL... urls) {
            // Obtener la conexión
            HttpURLConnection con = null;

            try {
                // Construir los datos a enviar
                String data = "body=" + URLEncoder.encode(comment,"UTF-8");

                con = (HttpURLConnection)urls[0].openConnection();

                // Activar método POST
                con.setDoOutput(true);

                // Tamaño previamente conocido
                con.setFixedLengthStreamingMode(data.getBytes().length);

                // Establecer application/x-www-form-urlencoded debido al formato clave-valor
                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                OutputStream out = new BufferedOutputStream(con.getOutputStream());

                out.write(data.getBytes());
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(con!=null)
                    con.disconnect();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            Toast.makeText(getBaseContext(), "Comentario posteado", Toast.LENGTH_LONG).show();
        }
    }
}
