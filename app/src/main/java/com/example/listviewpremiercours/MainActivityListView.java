package com.example.listviewpremiercours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.listviewpremiercours.controller.DaoPersonne;
import com.example.listviewpremiercours.model.Personne;

import java.util.ArrayList;

public class MainActivityListView extends AppCompatActivity {
    public static final String CURRENT_INDEX_PERSONNE = "current_index_personne";
    ListView listView;
    Personne personne;

    public static final String PERSONNE = "personne";
    ArrayAdapter<Personne> personneArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_view);
        listView = findViewById(R.id.listview);
        Intent intent = getIntent();
        ArrayList<Personne> personnes = (ArrayList<Personne>) intent.getSerializableExtra(MainActivity.LIST_PERSONNES);
        personneArrayAdapter = new ArrayAdapter<Personne>(this, android.R.layout.simple_list_item_1, personnes);
        listView.setAdapter(personneArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int index,
                                    long l) {
                personne = DaoPersonne.getAllPersonnes().get(index);
                Log.d(MainActivityListView.class.getSimpleName(), "setOnItemClickListener index = " + index);
                Intent intent1 = new Intent();
                setResult(RESULT_OK,
                        intent1.putExtra(PERSONNE, personne)
                                .putExtra(CURRENT_INDEX_PERSONNE, index)
                );
                Log.d(MainActivityListView.class.getSimpleName(),
                        "after finish() : " + intent1.getIntExtra(CURRENT_INDEX_PERSONNE, 99));
                finish();
            }
        });
    }


    public void retour(View view) {
        finish();
    }
}