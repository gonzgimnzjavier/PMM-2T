package com.example.a58internalstoragenombre;

import static android.provider.Telephony.Mms.Part.FILENAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText mEditText;
    EditText mEditTextFileName; //nombre del fichero a guardar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.editText);
        mEditTextFileName = findViewById(R.id.editTextFileName);
        Button buttonWrite = findViewById(R.id.buttonWrite);
        buttonWrite.setOnClickListener(view -> writeFile(view));
        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(view -> readFile(view));
    }

    public void writeFile(View view) {
        String fileName = mEditTextFileName.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(mEditText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(View view) {
        String fileName = mEditTextFileName.getText().toString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = openFileInput(fileName);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine = null;
                while ((newLine = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(newLine+"\n");
                }
                inputStream.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        mEditText.setText(stringBuilder);
    }
}