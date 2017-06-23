package com.example.sean.initiativetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ModifyCharacter extends AppCompatActivity {

    private LinearLayout layout;

    private ArrayList<CharacterInfo> characters;
    private Button[] buttonCharacters;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_character);

        layout = (LinearLayout)findViewById(R.id.layout);

        characters = getIntent().getExtras().getParcelableArrayList("characterList");

        buttonCharacters = new Button[characters.size()];

        // Populates the buttons and adds them to the layout for display
        for (int i = 0; i < buttonCharacters.length; i++) {
            // This is cheating, but does work so I'm leaving it in
            final int x = i;
            buttonCharacters[i] = new Button(this);
            buttonCharacters[i].setText(characters.get(i).getName());
            buttonCharacters[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editStats(characters.get(x));
                }
            });
            layout.addView(buttonCharacters[i]);
        }

        // Self explanatory, a button that executes another method to pass the new updated list of
        // characters to the main screen
        save = new Button(this);
        save.setText("Save changes");
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveChanges(1);
            }
        });
        layout.addView(save);
    }

    public void editStats(CharacterInfo character) {
        /* Ok, this is kind of annoying to explain
         * Basically, it creates a new dialog that appears that has fields for the user to
         * edit and change the stats of the character
         * The hard part is making the layout look nice programtically though
         */
        final int location = characters.indexOf(character);
        AlertDialog.Builder editCharacter = new AlertDialog.Builder(this);

        final EditText characterName = new EditText(this);
        final EditText initScore = new EditText(this);
        final EditText modScore = new EditText(this);

        characterName.setText(character.getName());
        initScore.setText(String.valueOf(character.getInitScore()));
        modScore.setText(String.valueOf(character.getMod()));

        initScore.setInputType(InputType.TYPE_CLASS_NUMBER);
        modScore.setInputType(InputType.TYPE_CLASS_NUMBER);

        editCharacter.setMessage("Change stats: ");
        editCharacter.setTitle("Stats of " + character.getName());

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(characterName);
        layout.addView(initScore);
        layout.addView(modScore);

        editCharacter.setView(layout);


        editCharacter.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                characters.get(location).setName(characterName.getText().toString());
                characters.get(location).setInitScore(Integer.valueOf(initScore.getText().toString()));
                characters.get(location).setMod(Integer.valueOf(modScore.getText().toString()));
            }
        });

        editCharacter.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        editCharacter.setNegativeButton("Delete Character", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                characters.remove(location);
                saveChanges(0);
            }
        });
        editCharacter.show();
    }

    public void saveChanges(int choice) {
        /* Dear person who is reading my code.
         * I am so sorry, but this is honestly the best way I thought about doing this
         * Here's what happens.
         * In this class, there are two instances when saveChanges is called, whenever the user
         * is done making changes, or when the user deletes a character.
         * The problem with deleting a character, is that it can't delete the button at the same
         * time.
         * My solution? Re launch the class with the character gone.
         * Is it a good solution? Eh. Does it work? Hell yes.
         */

        Intent intent;
        if(choice == 1) {
            intent = new Intent(this, CreateTurnOrder.class);
        } else {
            intent = new Intent(this, ModifyCharacter.class);
        }
        intent.putExtra("characterList", characters);

        startActivity(intent);
    }

}
