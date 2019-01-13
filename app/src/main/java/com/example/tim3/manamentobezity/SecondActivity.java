package com.example.tim3.manamentobezity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    // statické globálne premenné pre prácu s údajmi
    public static String WEIGHT_KEY = "weightKey";
    public static String SYSTOLIC_KEY = "0";
    public static String DIASTOLIC_KEY = "1";
    public static String HEARTBEATS_KEY = "heartbeatsKey";
    public static String NOTES_KEY = "notesKey";
    public static String NAME_KEY = "nameKey";
    public static String SURNAME_KEY = "surnameKey";
    public static String GENDER_KEY = "genderKey";
    public static String EMAIL_KEY = "";
    public static String AGE_KEY = "ageKey";
    public static String LOCATION_KEY = "locationKey";

    // premenné pre prácu s emailom
    private String email;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    // funkcia, ktorá vyplní todo: co spravi?
    private void PrefillFormular(Intent intent){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");      //chcem dostat formatovany datum
        Calendar c = Calendar.getInstance();

        //do statických premenných uloží údaje z minulej aktivity
        SecondActivity.NAME_KEY = MainActivity.NAME.getString("name");       //do statickych premennych uklada udaje z min. aktivity
        SecondActivity.SURNAME_KEY = MainActivity.SURNAME.getString("surname");
        SecondActivity.GENDER_KEY = MainActivity.GENDER.getString("gender");
        SecondActivity.EMAIL_KEY = MainActivity.EMAIL.getString("email");
        SecondActivity.AGE_KEY = MainActivity.AGE.getString("age");
        SecondActivity.LOCATION_KEY = MainActivity.LOCATION.getString("location");

        // práca s mailom
        email = MainActivity.EMAIL.getString("email");   // adresa, na ktorú sa správa odošle
        // formát správy, ktorá obsahuje zadané údaje
        message =   "Meno: " + SecondActivity.NAME_KEY + "\n" +
                    "Priezvisko: " + SecondActivity.SURNAME_KEY + "\n" +
                    "Pohlavie: " + SecondActivity.GENDER_KEY + "\n" +
                    "Email: " + SecondActivity.EMAIL_KEY + "\n" +
                    "Vek: " + SecondActivity.AGE_KEY + " rokov" + "\n" +
                    "Okres: " + SecondActivity.LOCATION_KEY + "\n" +
                    "Hmotnosť: " + intent.getStringExtra(SecondActivity.WEIGHT_KEY) + " kg" + "\n\n" +
                    "Tep: " + intent.getStringExtra(SecondActivity.HEARTBEATS_KEY) + " bpm" + "\n" +
                    "Systolický tlak: " + intent.getStringExtra(SecondActivity.SYSTOLIC_KEY) + " mmHg" + "\n" +
                    "Diastolický tlak: " + intent.getStringExtra(SecondActivity.DIASTOLIC_KEY) + " mmHg" + "\n" +
                    "Lieky: " + intent.getStringExtra(SecondActivity.NOTES_KEY) + "\n" + "\n" +
                    "Dátum vyplnenia: " + Calendar.getInstance().getTime() + "\n"      //LocalDateTime.now()
                    ;
    }

    // funkcia vloží do statických premenných hodnoty z komponentov formuláru
    private void FillIntent(Intent intent){
        // váha
        EditText weightEditText = findViewById(R.id.Weight);
        intent.putExtra(WEIGHT_KEY, weightEditText.getText().toString());

        // systolický tlak
        EditText systolicEditText = findViewById(R.id.Systolic);
        intent.putExtra(SYSTOLIC_KEY, systolicEditText.getText().toString());

        // diastolický tlak
        EditText diastolicEditText = findViewById(R.id.Diastolic);
        intent.putExtra(DIASTOLIC_KEY, diastolicEditText.getText().toString());

        // počet úderov srdca
        EditText heartbeatsEditText = findViewById(R.id.Heartbeats);
        intent.putExtra(HEARTBEATS_KEY, heartbeatsEditText.getText().toString());

        // poznámky o liekoch
        EditText notesEditText = findViewById(R.id.Notes);
        intent.putExtra(NOTES_KEY, notesEditText.getText().toString());
    }

    // skontroluje správnosť zadaných údajov povinných parametrov
    private Boolean IsValid(){
        EditText weightEditText = findViewById(R.id.Weight);
        EditText heartbeatsEditText = findViewById(R.id.Heartbeats);
        EditText systolicEditText = findViewById(R.id.Systolic);
        EditText diastolicEditText = findViewById(R.id.Diastolic);

        // pomocné premenné na overenie jednotlivých zadaných údajov
        boolean weightCorrect = false;
        boolean heartbeatsCorrect = false;
        boolean sysCorrect = false;
        boolean diaCorrect = false;

        // skontroluj, či je správne zadaná váha
        if (weightEditText != null ){
            String weight =  weightEditText.getText().toString().trim();
            if  (weight == null || weight.isEmpty() || !MainActivity.checkIfNumber(weight)){
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Hmotnosť (číslo), vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast1.show();
                weightEditText.requestFocus();
            }
            else{
                weightCorrect = true;
            }
        }

        // skontroluj, či je správne zadaný tep srdca
        if (heartbeatsEditText != null ){
            String heartbeats =  heartbeatsEditText.getText().toString().trim();
            if  (heartbeats == null || heartbeats.isEmpty() || !MainActivity.checkIfNumber(heartbeats)){
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Srdcový tep (číslo), vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast2.show();
                heartbeatsEditText.requestFocus();
            }
            else{
                heartbeatsCorrect = true;
            }
        }

        // skontroluj, či je správne zadaný systolický tlak
        if (systolicEditText != null ){
            String systolic =  systolicEditText.getText().toString().trim();
            if  (systolic == null || systolic.isEmpty() || !MainActivity.checkIfNumber(systolic)){
                Toast toast3 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Systolický tlak (číslo), vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast3.show();
                systolicEditText.requestFocus();
            }
            else{
                sysCorrect = true;
            }
        }

        // skontroluj, či je správne zadaný diastolický tlak
        if (diastolicEditText != null ){
            String diastolic =  diastolicEditText.getText().toString().trim();
            if  (diastolic == null || diastolic.isEmpty() || !MainActivity.checkIfNumber(diastolic)){
                Toast toast4 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Diastolický tlak (číslo), vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast4.show();
                diastolicEditText.requestFocus();
            }
            else{
                diaCorrect = true;
            }
        }

        // over, či sú všetky hodnoty v textových poliach sú správne vyplnené
        if( weightCorrect && heartbeatsCorrect && sysCorrect && diaCorrect)
            return true;
        else
            return false;
    }

    // funkcia ktorá odošle zadané údaje na zadaný email po stlačení tlačidla odoslať
    public void sendForm(View view){
        if (IsValid()){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");

            FillIntent(i);
            PrefillFormular(i);

            // vytvorí email
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});           // prijímateľ
            i.putExtra(Intent.EXTRA_SUBJECT, "Zber dát ISZ 2018");      // predmet správy
            i.putExtra(Intent.EXTRA_TEXT, message);                           // obsah správy

            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(SecondActivity.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
