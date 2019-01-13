package com.example.tim3.manamentobezity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // statické globálne premenné pre prácu s údajmi
    public static String NAME_KEY = "nameKey";
    public static String SURNAME_KEY = "surnameKey";
    public static String GENDER_KEY = "muž";
    public static String EMAIL_KEY = "emailKey";
    public static String AGE_KEY = "ageKey";
    public static String LOCATION_KEY = "locationKey";
    public static Boolean genderChecked = true;

    // todo: co je to bundle ?
    public static Bundle NAME = new Bundle();
    public static Bundle SURNAME = new Bundle();
    public static Bundle GENDER = new Bundle();
    public static Bundle EMAIL = new Bundle();
    public static Bundle AGE = new Bundle();
    public static Bundle LOCATION = new Bundle();

    // práca s radio buttons
    private RadioGroup radioGroup;
    // private Button btnDisplay;
    public String gender = "muž";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerRadioButton();
        FillEmail();
    }

    private void FillEmail() {
        EditText emailEditText = findViewById(R.id.Email);
    }

    // skontroluje správnosť zadaných údajov povinných parametrov
    private Boolean IsValid(){
        EditText nameEditText = findViewById(R.id.Name);
        EditText emailEditText = findViewById(R.id.Email);
        EditText ageEditText = findViewById(R.id.Age);

        // pomocné premenné na overenie jednotlivých zadaných údajov
        boolean nameCorrect = false;
        boolean emailCorrect = false;
        boolean ageCorrect = false;
        //boolean genderCorrect = false;

        // skontroluj, či je správne zadané meno
        if (nameEditText != null ){
            String name =  nameEditText.getText().toString().trim();
            // ak hodnota nie je zadaná
            if  (name == null || name.isEmpty()){
                // pop-up okno, ktoré upozorní, že meno je povinný atribút // todo ? spravny komentar
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Meno, vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast1.show();

                nameEditText.requestFocus();
            }
            else{
                // textové pole s menom je správne vyplnené
                nameCorrect = true;
            }
        }

        // skontroluj, či je správne zadaný email
        if (emailEditText != null ){
            String email =  emailEditText.getText().toString().trim();
            // ak hodnota nie je zadaná
            if  (email == null || email.isEmpty() || !isValidEmail(email)){
                // toast in android
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Email, vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast2.show();
                emailEditText.requestFocus();
            }
            else{
                emailCorrect = true;
            }
        }

        // skontroluj, či je správne zadaný vek
        if (ageEditText != null ){
            String age =  ageEditText.getText().toString().trim();
            if  (age == null || age.isEmpty() || !checkIfNumber(age)){
                Toast toast3 = Toast.makeText(getApplicationContext(),
                        "Musíte správne vyplniť údaj Vek (číslo), vyznačený hviezdičkou.",
                        Toast.LENGTH_SHORT);
                toast3.show();
                ageEditText.requestFocus();
            }
            else{
                ageCorrect = true;
            }
        }

        // skontroluj, či je stlačený vybraný radiobutton
        /*if(genderChecked ){
            genderCorrect = true;
        }*/

        //if( nameCorrect && emailCorrect && ageCorrect && genderCorrect)

        // over, či sú všetky hodnoty v textových poliach sú správne vyplnené
        // a je vybraný radio button určujúci gender
        if( nameCorrect && emailCorrect && ageCorrect && genderChecked)
            return true;
        else
            return false;
    }

    // skontroluje či zadaný parameter je číslo
    // je používaná aj v SecondActivity preto public static
    public static boolean checkIfNumber(String age) {
        try {
            // int tmp = Integer.parseInt(str);
            Integer.parseInt(age);
        }
        catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    // overí, či má email správnu formu - vyhovuje regulárnemu výrazu
    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // funkcia otvorí dalšiu aktivitu (okno aplikácie) ak sú všetky parametre správne zadané
    public void openNextActivity(View view){
        if (IsValid()){
            Intent intent = new Intent(this, SecondActivity.class);
            FillIntent(intent);
            startActivity(intent);
        }
    }

    // funkcia vloží do statických premenných hodnoty z komponentov formuláru
    private void FillIntent(Intent intent){
        // meno
        EditText nameEditText = findViewById(R.id.Name);
        intent.putExtra(NAME_KEY, nameEditText.getText().toString());

        // priezvisko
        EditText surnameEditText = findViewById(R.id.Surname);
        intent.putExtra(SURNAME_KEY, surnameEditText.getText().toString());

        // email
        EditText emailEditText = findViewById(R.id.Email);
        intent.putExtra(EMAIL_KEY, emailEditText.getText().toString());

        // vek
        EditText ageEditText = findViewById(R.id.Age);
        intent.putExtra(AGE_KEY, ageEditText.getText().toString());

        // okres
        EditText locationEditText = findViewById(R.id.Location);
        intent.putExtra(LOCATION_KEY, locationEditText.getText().toString());

        MainActivity.GENDER.putString("gender", "" + gender);
        MainActivity.NAME.putString("name",nameEditText.getText().toString());
        MainActivity.SURNAME.putString("surname",surnameEditText.getText().toString());
        MainActivity.LOCATION.putString("location",locationEditText.getText().toString());
        MainActivity.EMAIL.putString("email",emailEditText.getText().toString());
        MainActivity.AGE.putString("age",ageEditText.getText().toString());
    }

    // funkcia zistí, ktorý radio button je zaškrknutý
    private void addListenerRadioButton() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        Button btnDisplay = (Button) findViewById(R.id.Female);
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                if(selectedId == 2131165190)
                    gender = "žena";
                else
                    gender = "muž";
            }
        });
    }
}
