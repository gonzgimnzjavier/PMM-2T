package com.example.a62sqllite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//al hacer click corto sobre un elemento de la lista, cambiamos la funcionalidad del toast a colocar word y definition en los edittext

public class MainActivity extends AppCompatActivity {

    EditText mEditTextWord;
    EditText mEditTextDefinition;
    DictionaryDatabase mDB;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DictionaryDatabase(this);
        mEditTextWord = findViewById(R.id.editTextWord);
        mEditTextDefinition = findViewById(R.id.editTextDefinition);
        Button buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecord();
            }
        });
        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mDB.getDefinition(id),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Records deleted = " + mDB.deleteRecord(id), Toast.LENGTH_SHORT).show();
                updateWordList();
                return true;
            }
        });
        updateWordList();
    }

    private void saveRecord() {
        mDB.saveRecord(mEditTextWord.getText().toString(), mEditTextDefinition.getText().toString());
        mEditTextWord.setText("");
        mEditTextDefinition.setText("");
        updateWordList();
    }

    private void updateWordList() {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getWordList(),
                new String[]{ "word"},
        new int[]{android.R.id.text1}, 0);
    }
    public Cursor getWordList() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id, " + FIELD_WORD +" FROM " + TABLE_DICTIONARY + " ORDER BY " + FIELD_WORD + " ASC";
        return db.rawQuery(query, null);
    }
}