package com.example.a56almacenpref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String NAME="NAME";
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.leer);
        SharedPreferences sharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, null);
        if (name != null) {
            textView.setText("HOLA");
        } else {
            textView.setText("Benvido de volta" + name);
        }
        mEditTextName = findViewById(R.id.escribir);

        //listener para el boton
        Button button = findViewById(R.id.guardar);
        button.setOnClickListener(view -> saveName(view));
    }
    private void saveName(View view) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString(NAME, mEditTextName.getText().toString());
        editor.commit();
    }
}


