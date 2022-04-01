package com.example.listviewpremiercours;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.listviewpremiercours.MainActivityListView.CURRENT_INDEX_PERSONNE;
import static com.example.listviewpremiercours.MainActivityListView.PERSONNE;
import static com.example.listviewpremiercours.R.id.editTextTextPersonName;
import static com.example.listviewpremiercours.R.id.editTextTextPersonName2;
import static com.example.listviewpremiercours.R.layout.activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listviewpremiercours.controller.DaoPersonne;
import com.example.listviewpremiercours.model.Personne;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    public static final String LIST_PERSONNES = "personnes";
    public static final int OUT_OF_BOUND_INDEX = -1;
    public static final String EMPTY_FIELD = "";
    private EditText eNom, ePrenom;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private int indiceSelected = -1;

    private void initializePersonFields(String emptyField,
                                        String emptyField2) {
        eNom.setText(emptyField);
        ePrenom.setText(emptyField2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        eNom = findViewById(editTextTextPersonName);
        ePrenom = findViewById(editTextTextPersonName2);

        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == RESULT_OK) {
                            Personne personne = (Personne) data.getSerializableExtra(PERSONNE);
                            initializePersonFields(personne.getNom(), personne.getPrenom());
                            indiceSelected = data.getIntExtra(CURRENT_INDEX_PERSONNE,
                                    OUT_OF_BOUND_INDEX);
                        }
                    }
                });
    }

    public void envoi(View view) {
        String nom, prenom;
        nom = eNom.getText().toString();
        prenom = ePrenom.getText().toString();
        Personne personne = new Personne(nom, prenom);
        DaoPersonne.addPersonne(personne);
        initializePersonFields(EMPTY_FIELD, EMPTY_FIELD);
        makeText(this,
                "personne added",
                LENGTH_SHORT).show();
    }

    public void afficher(View view) {
        Intent intent = new Intent(this,
                MainActivityListView.class);
        intent.putExtra(MainActivity.LIST_PERSONNES,
                (Serializable) DaoPersonne.getAllPersonnes());
        intentActivityResultLauncher.launch(intent);
    }

    public void supprimer(View view) {
        if (indiceSelected != OUT_OF_BOUND_INDEX)
            DaoPersonne.delPersonne(indiceSelected);
        initializePersonFields(EMPTY_FIELD, EMPTY_FIELD);
        makeText(this,
                "personne deleted",
                LENGTH_SHORT).show();
    }

    public void onClickModifyEvent(View view) {
        String nom, prenom;
        nom = eNom.getText().toString();
        prenom = ePrenom.getText().toString();
        Personne p = DaoPersonne.getAllPersonnes().get(indiceSelected);
        p.setNom(nom);
        p.setPrenom(prenom);
        DaoPersonne.modfify(indiceSelected, p);
        makeText(this,
                "personne modified",
                LENGTH_SHORT).show();
    }
}