package com.example.listviewpremiercours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listviewpremiercours.controller.DaoPersonne;
import com.example.listviewpremiercours.model.Personne;

public class MainActivity extends AppCompatActivity {
    public static final String LIST_PERSONNES = "personnes";
    public static final int OUT_OF_BOUND_INDEX = -1;
    EditText eNom, ePrenom;
    ActivityResultLauncher<Intent> intentActivityResultLauncher;
    int indiceSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eNom = findViewById(R.id.editTextTextPersonName);
        ePrenom = findViewById(R.id.editTextTextPersonName2);


        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Personne personne = (Personne) data.getSerializableExtra(MainActivityListView.PERSONNE);
                            eNom.setText(personne.getNom());
                            ePrenom.setText(personne.getPrenom());
                            indiceSelected = data.getIntExtra(MainActivityListView.CURRENT_INDEX_PERSONNE,-1);
                            Log.d(MainActivity.class.getSimpleName(), "indiceSelected = " + indiceSelected);
                        }
                    }
                });
    }

    public void envoi(View view) {
        String nom, prenom;
        nom = eNom.getText().toString();
        prenom = ePrenom.getText().toString();

        Personne personne = new Personne(nom, prenom);
        personne.setId(1);
        DaoPersonne.addPersonne(personne);
        eNom.setText("");
        ePrenom.setText("");
        Toast.makeText(this, "personne added", Toast.LENGTH_SHORT).show();


    }

    public void afficher(View view) {
        Intent intent = new Intent(this, MainActivityListView.class);
        intent.putExtra(MainActivity.LIST_PERSONNES, DaoPersonne.getAllPersonnes());
        //startActivity(intent);
        intentActivityResultLauncher.launch(intent);
    }

    public void supprimer(View view) {
        String nom, prenom;
        nom = eNom.getText().toString();
        prenom = ePrenom.getText().toString();
        // int indice = getIntent().getIntExtra(MainActivityListView.CURRENT_INDEX_PERSONNE, 99);
        Log.d(MainActivity.class.getSimpleName(), "indice = " + indiceSelected);
        Personne personne = new Personne(nom, prenom);
        personne.setId(1);
        if (indiceSelected != OUT_OF_BOUND_INDEX)
            DaoPersonne.delPersonne(
                    indiceSelected
            );
        eNom.setText("");
        ePrenom.setText("");
        Toast.makeText(this, "personne deleted", Toast.LENGTH_SHORT).show();

    }

    public void onClickModifyEvent(View view) {
        String nom, prenom;
        nom = eNom.getText().toString();
        prenom = ePrenom.getText().toString();
        DaoPersonne.modfify(indiceSelected,new Personne(nom,prenom));
    }
}