package com.example.sean.initiativetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class CreateTurnOrder extends AppCompatActivity {

    public ArrayList<CharacterInfo> characters = new ArrayList<>();

    public Typeface face;

    public EditText mCharName;
    public EditText mInitScore;
    public EditText mMod;

    public TextView baseRoll;
    public TextView initMod;

    public String charName;
    public int initScore;
    public int mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_turn_order);

        // This is only if the user is entering from ModifyCharacter
        if(getIntent().getExtras() != null) {
            characters = getIntent().getExtras().getParcelableArrayList("characterList");
        }

        face = Typeface.createFromAsset(getAssets(), "fonts/TrajanPro-Regular.ttf");

        // Makes the text accessible in Java
        mCharName = (EditText)findViewById(R.id.name);
        mInitScore = (EditText)findViewById(R.id.score);
        mMod = (EditText)findViewById(R.id.mod);

        baseRoll = (TextView)findViewById(R.id.baseRoll);
        initMod = (TextView)findViewById(R.id.initMod);

        // Sets all the fonts up and everything
        mCharName.setTypeface(face);
        mInitScore.setTypeface(face);
        mMod.setTypeface(face);
        baseRoll.setTypeface(face);
        initMod.setTypeface(face);

        /* In case I decide to switch back to spinners instead of a text field for entry
        Spinner spinScores = (Spinner)findViewById(R.id.listScores);
        Integer[] rollScores = new Integer[20];
        for (int i = 1; i <= 20; i++)
            rollScores[i-1] = i;
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, rollScores);
        spinScores.setAdapter(adapter);*/

    }

    public void addChar (View view) {

        // Get the values from all the text before creating a character
        charName = mCharName.getText().toString();
        initScore = Integer.valueOf(mInitScore.getText().toString());
        mod = Integer.valueOf(mMod.getText().toString());

        //Check for any duplicate names to avoid confusion
        //The replace all is to avoid any complications with things like "abc" and "abc  "
        if(sortNames(characters, charName.replaceAll("\\s","")) && !charName.equals("")) {

            // In DND, you can't have a roll above 20 or below 1, so this just prevents that
            if(initScore < 1 || initScore > 20) {
                AlertDialog negativeError = new AlertDialog.Builder(CreateTurnOrder.this).create();
                negativeError.setTitle("Error!");
                negativeError.setMessage("Invalid base roll inputted, remember you can only roll between 1-20");
                negativeError.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                negativeError.show();
            } else {
                characters.add(new CharacterInfo(charName, initScore, mod));

                //Resets the fields and notifies the user that they have sucessfully added a character
                mCharName.getText().clear();
                mInitScore.setText("1");
                mMod.setText("0");

                Context context = getApplicationContext();
                CharSequence notification = "Character sucessfully added";
                int time = Toast.LENGTH_SHORT;

                Toast.makeText(context, notification, time).show();
            }

        } else {
            //Creates the message that shows the user their error
            AlertDialog duplicateError = new AlertDialog.Builder(CreateTurnOrder.this).create();
            duplicateError.setTitle("Error!");
            duplicateError.setMessage("This name was previously inputted or is an invalid name (blank), please use a different name");
            duplicateError.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            duplicateError.show();
        }


    }

    public void modChar(View view) {
        // Pretty self explanatory, checks to see if the array actually has any people
        if(characters.isEmpty()) {
            AlertDialog emptyError = new AlertDialog.Builder(CreateTurnOrder.this).create();
            emptyError.setTitle("Error!");
            emptyError.setMessage("There are no characters created, please create at least one character to proceed");
            emptyError.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            emptyError.show();
        } else {
            Intent intent = new Intent(view.getContext(), ModifyCharacter.class);
            intent.putExtra("characterList", characters);
            startActivity(intent);
        }

    }

    public void startBattle(View view) {
        // Make sure that there are some characters in the battle
        if(characters.isEmpty()) {
            AlertDialog emptyError = new AlertDialog.Builder(CreateTurnOrder.this).create();
            emptyError.setTitle("Error!");
            emptyError.setMessage("There are no characters created, please create at least one character to proceed");
            emptyError.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            emptyError.show();
        } else {
            // Passes the array list of characters to the battle screen
            Collections.sort(characters, new CharacterInfoComparator());
            Intent intent = new Intent(view.getContext(), BattleScreen.class);
            intent.putExtra("characterList", characters);
            startActivity(intent);
        }
    }

    //Method to make sure there aren't any duplicate names (for neatness it's here)
    public boolean sortNames(ArrayList<CharacterInfo> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            //Basically, check i in list's name and make sure it doesn't equal name given;
            if(list.get(i).getName().replaceAll("\\s","").equals(name)) return false;
        }
        return true;
    }

}
