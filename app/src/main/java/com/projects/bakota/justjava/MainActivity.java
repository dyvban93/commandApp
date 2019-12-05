package com.projects.bakota.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantite = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /*
    * cette fonction permet de passer une commande
    * */
    public void passerCommande(View view) {

        EditText nom_edit = (EditText) findViewById(R.id.nom_edit_text);
        String nom = nom_edit.getText().toString();

        CheckBox mayo_check_box = (CheckBox) findViewById(R.id.mayo_check_box);
        boolean mayo_est_coche = mayo_check_box.isChecked();

        CheckBox pain_check_box = (CheckBox) findViewById(R.id.pain_check_box);
       boolean pain_est_coche = pain_check_box.isChecked();

        //on calcule le prix
        int prix = calculerPrix(mayo_est_coche,pain_est_coche);
        String message = resumerCommande(prix, mayo_est_coche, pain_est_coche,nom);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Commande pour: "+nom);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager() ) != null){
            startActivity(intent);
        }

    }

    public int calculerPrix(boolean  mayo, boolean pain){

        int prixBase = 5;

        if(mayo){
            // il a choisi la mayo comme option
            prixBase += 1;
        }

        // il veut du pain
        if(pain) prixBase += 2;

        return quantite * prixBase;
    }

    public String resumerCommande(int prix, boolean mayo, boolean pain, String nom){
        String message = getResources().getString(R.string.resumer,nom);
        message += "\nQuantite: "+quantite;
        message += "\nVeut Mayonnaise "+ mayo;
        message += "\nVeut pain "+pain;
        message += "\nTotal: "+prix +" FCFA";
        message +=  "\n"+getResources().getString(R.string.remerciements);
        return  message;
    }

    /*fonction pour aficher un message*/
    public void afficherMessage(int msg){

        TextView qte_text = (TextView) findViewById(R.id.quantite_txt);
        qte_text.setText(""+msg);
    }

    public void incrementer(View view) {

        if(quantite == 100){

            Toast.makeText(this,"Vous ne pouvez pas commander plus de 100", Toast.LENGTH_LONG).show();
            return;
        }
      quantite += 1;
        afficherMessage(quantite);
    }

    public void decrementer(View view) {
        if(quantite == 1){
            Toast.makeText(this,"Vous ne pouvez pas commander moins d\'un", Toast.LENGTH_LONG).show();
            return;
        }
        quantite -= 1;
        afficherMessage(quantite);
    }
}
