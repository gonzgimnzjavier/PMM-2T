package com.example.a60ficherosraw;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    EditText editTextRaw = findViewById(R.id.editText);

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    Button buttonReadRaw = findViewById(R.id.buttonReadRaw);
    editTextRaw.setText(
    getText(this.getResources().
    openRawResource(R.raw.mitexto)));
    button.setOnClickListener(view ->abreFicheroRaw(view));
}
private void abreFicheroRaw(View view) {
    //leemos el fichero raw
    editTextRaw.setText(
            getText(this.getResources().
                    openRawResource(R.raw.mitexto)));
}


// pasamos co,o parametro un is
private String getText(InputStream inputStream) {
    StringBuilder stringBuilder = new StringBuilder();
    try {;
        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new
                    InputStreamReader(inputStream);
            BufferedReader bufferedReader = new
                    BufferedReader(inputStreamReader);
            String newLine = null;
            while ((newLine = bufferedReader.readLine())
                    != null ) {
                stringBuilder.append(newLine+"\n");
            }
            inputStream.close();
        }
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
    return stringBuilder.toString();
}
}