package com.example.a59almacenexterno;

import static android.provider.Telephony.Mms.Part.FILENAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //permisos
    private final String[] PERMISOS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    //chequeos
    public boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    EditText mEditText;
    EditText mEditTextFileName; //nombre del fichero a guardar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //texto y nombre
        mEditText = findViewById(R.id.editText);
        mEditTextFileName = findViewById(R.id.editTextFileName);
        //leer y escribir
        Button buttonWrite = findViewById(R.id.buttonWrite);
        buttonWrite.setOnClickListener(view -> writeFile(view));
        Button buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(view -> readFile(view));
    }

    /*public void writeFile(View view) {
        String fileName = mEditTextFileName.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(mEditText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }*/
    public void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISOS_STORAGE, 101);
        }
    }

    /*public void readFile(View view) {
        String fileName = mEditTextFileName.getText().toString();//nombre fichero
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine = null;
                while ((newLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(newLine + "\n");
                }
                inputStream.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        mEditText.setText(stringBuilder);
    }*/

    public void writeFile(View view) {
        if (isExternalStorageWritable()) {
            checkStoragePermission();
            try {
                String fileName = "Documents/"+mEditTextFileName.getText().toString();
                File textFile = new File(Environment.getExternalStorageDirectory(), fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write(mEditText.getText().toString().getBytes());
                fileOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot write to External Storage", Toast.LENGTH_LONG).show();
        }
    }

    public void readFile(View view) {
        if (isExternalStorageReadable()) {
            checkStoragePermission();
            StringBuilder stringBuilder = new StringBuilder();
            try {
                String fileName = "Documents/"+mEditTextFileName.getText().toString();
                File textFile = new File(Environment.getExternalStorageDirectory(), fileName);
                FileInputStream fileInputStream = new FileInputStream(textFile);
                if (fileInputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String newLine = null;
                    while ((newLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(newLine + "\n");
                    }
                    fileInputStream.close();
                }
                mEditText.setText(stringBuilder);
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot read External Storage", Toast.LENGTH_LONG).show();
        }
    }
}